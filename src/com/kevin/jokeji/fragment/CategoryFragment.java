package com.kevin.jokeji.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kevin.jokeji.JokeApplication;
import com.kevin.jokeji.JokeListActivity;
import com.kevin.jokeji.R;
import com.kevin.jokeji.beans.Category;
import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.util.Fetcher;
import com.kevin.jokeji.util.ScreenUtil;

public class CategoryFragment extends Fragment implements OnItemClickListener {

	private GridView mCategoryGrid;
	private LayoutInflater mInflater;
	private CategoryAdapter mAdapter;
	private ArrayList<Category> mCategorys;

	private DiskLruCache mDiskLruCache = null;
	private JokeApplication mApplication;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (JokeApplication) getActivity().getApplication();
		mDiskLruCache = mApplication.getDiskLruCache();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		mInflater = inflater;

		View view = inflater.inflate(R.layout.frg_category_layout, container,
				false);
		mAdapter = new CategoryAdapter();
		mCategoryGrid = (GridView) view.findViewById(R.id.category_grid_view);
		mCategoryGrid.setOnItemClickListener(this);
		mCategoryGrid.setAdapter(mAdapter);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mCategorys = CacheHelper.getObjectToDisk(mDiskLruCache,
				URLS.CATEGORY_URL);
		if (mCategorys != null) {
			mAdapter.bindData(mCategorys);
			mAdapter.notifyDataSetChanged();
		} else {
			new CategoryAsync().execute(URLS.CATEGORY_URL);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getActivity(), JokeListActivity.class);
		intent.putExtra(JokeListActivity.CATEGORY, mCategorys.get(position)
				.getUrl());
		startActivity(intent);

	}

	private class CategoryAsync extends
			AsyncTask<String, Void, ArrayList<Category>> {

		@Override
		protected ArrayList<Category> doInBackground(String... params) {

			ArrayList<Category> categorys = Fetcher.getCategorys(params[0]);
			CacheHelper.saveObjectToDisk(mDiskLruCache, params[0], categorys);
			return categorys;
		}

		@Override
		protected void onPostExecute(ArrayList<Category> result) {
			mCategorys = result;
			mAdapter.bindData(mCategorys);
			mAdapter.notifyDataSetChanged();
		}

	}

	private class ViewHolder {
		private TextView title;
	}

	private class CategoryAdapter extends BaseAdapter {

		ArrayList<Category> categorys = new ArrayList<Category>();
		private int width;

		public void bindData(ArrayList<Category> categorys) {
			if (categorys != null) {
				this.categorys = categorys;
			}
		}

		public CategoryAdapter() {
			width = (int) (ScreenUtil.getScreenWidth(getActivity()) / (3 * 1.0));
		}

		@Override
		public int getCount() {
			return categorys.size();
		}

		@Override
		public Object getItem(int position) {
			return categorys.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {

				viewHolder = new ViewHolder();

				convertView = mInflater.inflate(
						R.layout.frg_category_item_layout, parent, false);

				viewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				LayoutParams params = (LayoutParams) viewHolder.title
						.getLayoutParams();
				params.width = width;
				params.height = width;

				convertView.setTag(viewHolder);

			}

			viewHolder = (ViewHolder) convertView.getTag();

			viewHolder.title.setText(categorys.get(position).getTitle());
			return convertView;
		}

	}

}

package com.kevin.jokeji.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.jokeji.JokeApplication;
import com.kevin.jokeji.JokeDetailActivity;
import com.kevin.jokeji.JokeListActivity;
import com.kevin.jokeji.R;
import com.kevin.jokeji.beans.JokeItem;
import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.util.Fetcher;
import com.kevin.jokeji.view.PinnedSectionListView.PinnedSectionListAdapter;

public class AwardsFragment extends ListFragment implements OnClickListener {

	private JokeAdapter mJokeAdapter;
	private ArrayList<JokeItem> mJokeItems;

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
		return inflater.inflate(R.layout.frg_award_layout, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeAdapter();

		mJokeItems = CacheHelper
				.getObjectToDisk(mDiskLruCache, URLS.AWARDS_URL);
		if (mJokeItems != null) {
			mJokeAdapter.bindData(mJokeItems);
			mJokeAdapter.notifyDataSetChanged();
		} else {
			new JokeAnsycTask().execute(URLS.AWARDS_URL);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		JokeItem item = (JokeItem) getListView().getAdapter().getItem(position);

		if (item.type == JokeItem.ITEM) {
			Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
			intent.putExtra(JokeDetailActivity.JOKE, item);
			startActivity(intent);
		} else if (item.type == JokeItem.MORE) {
			Intent intent = new Intent(getActivity(), JokeListActivity.class);
			intent.putExtra(JokeListActivity.CATEGORY, "/" + item.getUrl());
			startActivity(intent);
		}

	}

	private void initializeAdapter() {

		mJokeAdapter = new JokeAdapter(getActivity());
		setListAdapter(mJokeAdapter);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getActivity(), "Item: " + v.getTag(), Toast.LENGTH_SHORT)
				.show();
	}

	class JokeAnsycTask extends AsyncTask<String, Void, ArrayList<JokeItem>> {

		@Override
		protected ArrayList<JokeItem> doInBackground(String... params) {

			ArrayList<JokeItem> jokeItems = Fetcher.getAwards(params[0]);
			CacheHelper.saveObjectToDisk(mDiskLruCache, params[0], jokeItems);
			return jokeItems;
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(ArrayList<JokeItem> result) {
			mJokeItems = result;
			mJokeAdapter.bindData(mJokeItems);
			mJokeAdapter.notifyDataSetChanged();
		}

	}

	static class JokeAdapter extends BaseAdapter implements
			PinnedSectionListAdapter {

		private ArrayList<JokeItem> jokes = new ArrayList<JokeItem>();
		private LayoutInflater inflater;

		private class ViewHolder {
			TextView title;
			TextView count;
		}

		public JokeAdapter(Context context) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void bindData(ArrayList<JokeItem> jokes) {

			if (jokes != null) {
				this.jokes = jokes;
			}

		}

		@Override
		public int getCount() {
			return jokes.size();
		}

		@Override
		public Object getItem(int position) {
			return jokes.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			JokeItem item = jokes.get(position);
			ViewHolder viewHolder = null;

			if (item.type == JokeItem.SECTION) {

				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.award_list_section_item, parent, false);

					viewHolder = new ViewHolder();

					viewHolder.title = (TextView) convertView
							.findViewById(R.id.name);
					convertView.setTag(viewHolder);
				}

				viewHolder = (ViewHolder) convertView.getTag();
				viewHolder.title.setText(item.getTitle());

			} else if (item.type == JokeItem.ITEM) {

				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.frg_new_item_layout, parent, false);

					viewHolder = new ViewHolder();

					viewHolder.title = (TextView) convertView
							.findViewById(R.id.title);
					viewHolder.count = (TextView) convertView
							.findViewById(R.id.count);

					convertView.setTag(viewHolder);
				}

				viewHolder = (ViewHolder) convertView.getTag();

				viewHolder.title.setText(item.getTitle());
				viewHolder.count.setText(item.getCount());

			} else if (item.type == JokeItem.MORE) {

				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.award_list_more_item, parent, false);

					viewHolder = new ViewHolder();

					viewHolder.title = (TextView) convertView
							.findViewById(R.id.more);

					convertView.setTag(viewHolder);
				}

				viewHolder = (ViewHolder) convertView.getTag();

				viewHolder.title.setText(item.getTitle());

			}

			return convertView;
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		@Override
		public int getItemViewType(int position) {
			return jokes.get(position).type;
		}

		@Override
		public boolean isItemViewTypePinned(int viewType) {
			return viewType == JokeItem.SECTION;
		}

	}

}
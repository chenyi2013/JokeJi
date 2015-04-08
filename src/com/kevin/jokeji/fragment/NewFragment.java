package com.kevin.jokeji.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kevin.jokeji.JokeApplication;
import com.kevin.jokeji.JokeDetailActivity;
import com.kevin.jokeji.R;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.util.Fetcher;

import java.util.ArrayList;

public class NewFragment extends Fragment implements OnItemClickListener {

	public static final String URL = "url";

	private ListView mJokeListView;
	private ArrayList<Joke> mJokes;
	private LayoutInflater mInflater;

	private JokeAdapter mAdapter;
	private DiskLruCache mDiskLruCache = null;
	private JokeApplication mApplication;
	private boolean isRefresh = false;

	SwipeRefreshLayout mRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mInflater = inflater;
		return inflater.inflate(R.layout.frg_new_layout, container, false);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (JokeApplication) getActivity().getApplication();
		mDiskLruCache = mApplication.getDiskLruCache();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initView();
		loadData();
	}

	private void initView() {
		mAdapter = new JokeAdapter();

		mJokeListView = (ListView) getView().findViewById(R.id.joke_list_view);
		View footer = getActivity().getLayoutInflater().inflate(
				R.layout.more_item, mJokeListView, false);
		mJokeListView.addFooterView(footer);
		mJokeListView.setOnItemClickListener(this);
		mJokeListView.setAdapter(mAdapter);

		mRefreshLayout = (SwipeRefreshLayout) getView()
				.findViewById(R.id.swipe);

		mRefreshLayout.setColorSchemeResources(R.color.holo_red_light,
				R.color.holo_green_light,
				R.color.holo_blue_bright,
				R.color.holo_orange_light);
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				mRefreshLayout.setRefreshing(true);
				isRefresh = true;
				loadData();

			}
		});

	}

	private void loadData() {
		Bundle bundle = getArguments();
		if (bundle != null) {

			String url = bundle.getString(URL);

			if (isRefresh) {
				new JokeAnsycTask().execute(url);
			} else {
				mJokes = CacheHelper.getObjectToDisk(mDiskLruCache, url);
				if (mJokes != null) {
					mAdapter.bindData(mJokes);
					mAdapter.notifyDataSetChanged();
				} else {
					new JokeAnsycTask().execute(url);
				}
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

        if(mJokes == null){
            return ;
        }
		if (position < mJokes.size() - 1 + mJokeListView.getFooterViewsCount()) {
			Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
			intent.putExtra(JokeDetailActivity.JOKE, mJokes.get(position));
			startActivity(intent);
		} else if (position == mJokes.size() - 1
				+ mJokeListView.getFooterViewsCount()) {

		}

	}

	private class ViewHolder {
		TextView title;
		TextView count;
		TextView data;
	}

	class JokeAnsycTask extends AsyncTask<String, Void, ArrayList<Joke>> {

		@Override
		protected ArrayList<Joke> doInBackground(String... params) {

			ArrayList<Joke> jokes = Fetcher.getJokes(params[0]);
			CacheHelper.saveObjectToDisk(mDiskLruCache, params[0], jokes);
			return jokes;
		}

		@Override
		protected void onPostExecute(ArrayList<Joke> result) {
			mJokes = result;
			isRefresh = false;
			mRefreshLayout.setRefreshing(false);
            if(mJokes != null){
                mAdapter.bindData(mJokes);
                mAdapter.notifyDataSetChanged();
            }
		}

	}

	class JokeAdapter extends BaseAdapter {

		private ArrayList<Joke> jokes = new ArrayList<Joke>();

		public void bindData(ArrayList<Joke> jokes) {

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

			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.frg_new_item_layout,
						parent, false);
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.data = (TextView) convertView
						.findViewById(R.id.data);
				viewHolder.count = (TextView) convertView
						.findViewById(R.id.count);
				convertView.setTag(viewHolder);
			}

			Joke joke = jokes.get(position);

			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.title.setText(joke.getTitle());
			viewHolder.count.setText(joke.getCount());
			viewHolder.data.setText(joke.getData());

			return convertView;
		}
	}

}

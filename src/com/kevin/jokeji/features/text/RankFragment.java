package com.kevin.jokeji.features.text;

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
import android.widget.ListView;
import android.widget.TextView;

import com.kevin.jokeji.JokeApplication;
import com.kevin.jokeji.JokeDetailActivity;
import com.kevin.jokeji.R;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.util.Fetcher;

public class RankFragment extends Fragment implements OnItemClickListener {

	public static final String JOKE = "joke";

	private ListView mJokeListView;
	private ArrayList<Joke> mJokes;
	private LayoutInflater mInflater;

	private JokeAdapter mAdapter;

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
		return inflater.inflate(R.layout.frg_rank_layout, container, false);
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
		mJokeListView.setOnItemClickListener(this);
		mJokeListView.setAdapter(mAdapter);

	}

	private void loadData() {

		mJokes = CacheHelper.getObjectToDisk(mDiskLruCache, URLS.RANK_URL);
		if (mJokes != null) {
			mAdapter.bindData(mJokes);
			mAdapter.notifyDataSetChanged();
		} else {
			new JokeAnsycTask().execute(URLS.RANK_URL);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
		intent.putExtra(JOKE, mJokes.get(position));
		startActivity(intent);

	}

	private class ViewHolder {
		TextView title;
		TextView count;
		TextView data;
	}

	class JokeAnsycTask extends AsyncTask<String, Void, ArrayList<Joke>> {

		@Override
		protected ArrayList<Joke> doInBackground(String... params) {

			ArrayList<Joke> jokes = Fetcher.getRanks(params[0]);
			CacheHelper.saveObjectToDisk(mDiskLruCache, params[0], jokes);
			return jokes;
		}

		@Override
		protected void onPostExecute(ArrayList<Joke> result) {
			mJokes = result;
			mAdapter.bindData(mJokes);
			mAdapter.notifyDataSetChanged();
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

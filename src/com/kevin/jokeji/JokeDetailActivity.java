package com.kevin.jokeji;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.util.Fetcher;

public class JokeDetailActivity extends Activity {

	private TextView mJokeDetailTv;
	private Joke mJoke;
	private String mContent;
	public static final String JOKE = "joke";

	private DiskLruCache mDiskLruCache = null;
	private JokeApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joke_detail);
		mApplication = (JokeApplication) getApplication();
		mDiskLruCache = mApplication.getDiskLruCache();
		initView();
		loadData();
	}

	private void initView() {
		mJokeDetailTv = (TextView) findViewById(R.id.joke_detail);
	}

	private void loadData() {
		Intent intent = getIntent();
		if (intent != null) {

			mJoke = (Joke) intent.getSerializableExtra(JOKE);
			if (mJoke != null) {

				String url = URLS.HOST + mJoke.getUrl();

				mContent = CacheHelper.getObjectToDisk(mDiskLruCache, url);
				if (mContent != null) {
					mJokeDetailTv.setText(Html.fromHtml(mContent));
				} else {
					new JokeDetailSyncTask().execute(url);
				}

			}

		}
	}

	class JokeDetailSyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			String content = Fetcher.getJoke(params[0]);
			CacheHelper.saveObjectToDisk(mDiskLruCache, params[0], content);
			return content;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				mJokeDetailTv.setText(Html.fromHtml(result));
			}
		}

	}
}

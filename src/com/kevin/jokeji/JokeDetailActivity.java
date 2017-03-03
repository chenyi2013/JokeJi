package com.kevin.jokeji;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import com.kevin.jokeji.base.BaseActivity;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.util.Fetcher;

public class JokeDetailActivity extends BaseActivity {

    private TextView mJokeDetailTv;
    private Joke mJoke;
    private String mContent;
    public static final String JOKE = "joke";

    private DiskLruCache mDiskLruCache = null;
    private JokeApplication mApplication;


    protected void initView() {

        mJokeDetailTv = (TextView) findViewById(R.id.joke_detail);
    }

    @Override
    protected void initData() {
        mApplication = (JokeApplication) getApplication();
        mDiskLruCache = mApplication.getDiskLruCache();
    }

    protected void loadData() {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joke_detail;
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

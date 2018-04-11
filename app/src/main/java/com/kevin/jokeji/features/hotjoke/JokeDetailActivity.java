package com.kevin.jokeji.features.hotjoke;

import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseActivity;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

public class JokeDetailActivity extends BaseActivity implements BaseView<String> {

    public static final String JOKE = "joke";

    private TextView mJokeDetailTv;
    private Joke mJoke;

    private CommonPresenter<String> mPresenter;


    @Override
    protected void initView() {

        mJokeDetailTv = (TextView) findViewById(R.id.joke_detail);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mJoke = (Joke) intent.getSerializableExtra(JOKE);
        }
        mPresenter = new CommonPresenter<>(new JokeDetailModel(), this);
    }

    @Override
    protected void loadData() {
        if (mJoke != null) {
            showLoading();
            mPresenter.loadData(URLS.HOST + mJoke.getUrl(), true);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joke_detail;
    }

    @Override
    public void showData(String joke, boolean isRefresh) {
        showContent();
        mJokeDetailTv.setText(Html.fromHtml(joke));
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        loadData();
    }

    @Override
    public void showError() {
        showErrorInfo();
    }


}

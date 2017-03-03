package com.kevin.jokeji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.kevin.jokeji.base.BaseActivity;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.hotjoke.HotJokeFragment;

public class JokeListActivity extends BaseActivity {

    public static final String CATEGORY = "category";

    private FragmentManager mManager;
    private HotJokeFragment mFragment;

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joke_list;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {
        mManager = getSupportFragmentManager();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(CATEGORY)) {

            String path = intent.getStringExtra(CATEGORY);

            mFragment = new HotJokeFragment();
            Bundle bundle = new Bundle();
            bundle.putString(HotJokeFragment.URL, URLS.HOST + path);
            mFragment.setArguments(bundle);
            mManager.beginTransaction().add(R.id.content, mFragment).commit();

        }
    }
}

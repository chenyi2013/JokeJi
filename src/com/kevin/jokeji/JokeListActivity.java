package com.kevin.jokeji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.fragment.NewFragment;

public class JokeListActivity extends ActionBarActivity {

	public static final String CATEGORY = "category";

	private FragmentManager mManager;
	private NewFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joke_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.logo);
        actionBar.setDisplayUseLogoEnabled(true);

		mManager = getSupportFragmentManager();
		Intent intent = getIntent();
		if (intent != null && intent.hasExtra(CATEGORY)) {

			String path = intent.getStringExtra(CATEGORY);

			mFragment = new NewFragment();
			Bundle bundle = new Bundle();
			bundle.putString(NewFragment.URL, URLS.HOST + path);
			mFragment.setArguments(bundle);
			mManager.beginTransaction().add(R.id.content, mFragment).commit();

		}

	}
}

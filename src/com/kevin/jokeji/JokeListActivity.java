package com.kevin.jokeji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.fragment.NewFragment;

public class JokeListActivity extends FragmentActivity {

	public static final String CATEGORY = "category";

	private FragmentManager mManager;
	private NewFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joke_list);
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

package com.kevin.jokeji;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kevin.jokeji.fragment.NewFragment;
import com.kevin.jokeji.fragment.TextFragment;

public class MainActivity extends ActionBarActivity implements
		OnCheckedChangeListener {

	private RadioGroup mBottomMenu;
	private Fragment mNewFragment;
	private Fragment mTextFragment;

	private FragmentManager mManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mManager = getSupportFragmentManager();
		initView();
	}

	private void initView() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.logo);
        actionBar.setDisplayUseLogoEnabled(true);


        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#33B5E5"));
        actionBar.setBackgroundDrawable(colorDrawable);


		mBottomMenu = (RadioGroup) findViewById(R.id.bottom_menu);
		mBottomMenu.setOnCheckedChangeListener(this);

		mNewFragment = new NewFragment();
		Bundle newBundle = new Bundle();
		newBundle.putString(NewFragment.URL, "http://www.jokeji.cn/list.htm");
		mNewFragment.setArguments(newBundle);

		mTextFragment = new TextFragment();
		mManager.beginTransaction().add(R.id.content, mNewFragment).commit();

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.last_new:
			mManager.beginTransaction().replace(R.id.content, mNewFragment)
					.commit();
			break;
		case R.id.text:
			mManager.beginTransaction().replace(R.id.content, mTextFragment)
					.commit();
			break;

		default:
			break;
		}

	}
}

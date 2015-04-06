package com.kevin.jokeji;

import com.kevin.jokeji.fragment.NewFragment;
import com.kevin.jokeji.fragment.TextFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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

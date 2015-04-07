package com.kevin.jokeji.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kevin.jokeji.R;

public class TextFragment extends Fragment implements OnCheckedChangeListener,
		ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;
	private RadioGroup mTopMenu;
	private ArrayList<Fragment> mFragments;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.frg_text_layout, container, false);
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mViewPager.setOnPageChangeListener(this);
		mTopMenu = (RadioGroup) view.findViewById(R.id.top_menu);
		mTopMenu.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mFragments = new ArrayList<Fragment>();

		mFragments.add(new CategoryFragment());
		mFragments.add(new RankFragment());
		mFragments.add(new AwardsFragment());

		mViewPager.setAdapter(new CotegoryAdapter(getChildFragmentManager()));
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.category_radio:
			mViewPager.setCurrentItem(0, true);
			break;
		case R.id.rank_radio:
			mViewPager.setCurrentItem(1, true);
			break;
		case R.id.award_radio:
			mViewPager.setCurrentItem(2, true);
			break;

		default:
			break;
		}

	}

	class CotegoryAdapter extends FragmentPagerAdapter {

		public CotegoryAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {

			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {

		switch (position) {
		case 0:

			mTopMenu.check(R.id.category_radio);

			break;
		case 1:
			mTopMenu.check(R.id.rank_radio);
			break;
		case 2:
			mTopMenu.check(R.id.award_radio);
			break;

		}

	}

}

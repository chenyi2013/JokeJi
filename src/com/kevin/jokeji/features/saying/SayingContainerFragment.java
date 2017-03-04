package com.kevin.jokeji.features.saying;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.config.URLS;

import java.util.ArrayList;


public class SayingContainerFragment extends BaseFragment {

    private String mTitles[] = {"名言", "诗句", "台词", "日志", "段子"};
    ViewPager mPager;
    SlidingTabLayout mTabLayout;
    ArrayList<Fragment> fragments;


    public SayingContainerFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.sliding_tab_layout);
        mPager.setAdapter(new SayingContainerAdapter(getChildFragmentManager()));
        mTabLayout.setViewPager(mPager, mTitles);
    }

    @Override
    protected void initData() {

        fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fragments.add(SayingFragment.create(URLS.SAYINGS[i]));
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_saying_container;
    }


    class SayingContainerAdapter extends FragmentPagerAdapter {

        public SayingContainerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}

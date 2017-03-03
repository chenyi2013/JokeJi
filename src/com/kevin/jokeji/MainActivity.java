package com.kevin.jokeji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kevin.jokeji.base.BaseActivity;
import com.kevin.jokeji.features.image.ImageFragment;
import com.kevin.jokeji.features.hotjoke.HotJokeFragment;
import com.kevin.jokeji.features.saying.SayingContainerFragment;
import com.kevin.jokeji.features.saying.SayingFragment;
import com.kevin.jokeji.features.text.TextFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements
        OnCheckedChangeListener {

    private RadioGroup mBottomMenu;
    private Fragment mNewFragment;
    private Fragment mTextFragment;
    private Fragment mImageFragment;
    private ViewPager mViewPager;

    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void loadData() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    protected void initView() {

        mBottomMenu = (RadioGroup) findViewById(R.id.bottom_menu);
        mBottomMenu.setOnCheckedChangeListener(this);

        mViewPager = (ViewPager) findViewById(R.id.content);


        mNewFragment = new HotJokeFragment();
        Bundle newBundle = new Bundle();
        newBundle.putString(HotJokeFragment.URL, "http://www.jokeji.cn/list.htm");
        mNewFragment.setArguments(newBundle);

        mTextFragment = new TextFragment();

        mImageFragment = new ImageFragment();

        fragments.add(mNewFragment);
        fragments.add(mTextFragment);
        fragments.add(mImageFragment);
        fragments.add(new SayingContainerFragment());

        mViewPager.setAdapter(new JokeJiAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mBottomMenu
                        .getChildAt(position)
                        .performClick();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    class JokeJiAdapter extends FragmentPagerAdapter {

        public JokeJiAdapter(FragmentManager fm) {
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

    @Override
    protected void initData() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            if (checkedId == group.getChildAt(i).getId()) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}

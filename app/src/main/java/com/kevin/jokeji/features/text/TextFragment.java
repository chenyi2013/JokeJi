package com.kevin.jokeji.features.text;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;

import java.util.ArrayList;

public class TextFragment extends BaseFragment implements OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private RadioGroup mTopMenu;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mTopMenu = findViewById(R.id.top_menu);
    }

    @Override
    protected void setListener() {
        mTopMenu.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new CategoryFragment());
        mFragments.add(new RankFragment());
        mFragments.add(new AwardsFragment());
    }

    @Override
    protected void loadData() {
        mViewPager.setAdapter(new CotegoryAdapter(getChildFragmentManager()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_text_layout;
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
        mTopMenu.getChildAt(position).performClick();
    }

}

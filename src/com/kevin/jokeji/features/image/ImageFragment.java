package com.kevin.jokeji.features.image;


import android.widget.ListView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.base.listview.CommonAdapter;
import com.kevin.jokeji.beans.Image;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class ImageFragment extends BaseFragment implements BaseView<ArrayList<Image>>, BGARefreshLayout.BGARefreshLayoutDelegate {

    private ListView mListView;
    private CommonPresenter<ArrayList<Image>> mPresenter;
    private CommonAdapter<Image> imageCommonAdapter;
    private BGARefreshLayout mRefreshLayout;


    public ImageFragment() {
    }

    @Override
    protected void initView() {
        mListView = findViewById(R.id.list_view);
        mRefreshLayout = findViewById(R.id.pull_to_refresh);
        mListView.setAdapter(imageCommonAdapter = new ImageAdapter(getActivity(), R.layout.image_item, null));

        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.drawable.logo);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_refreshing);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);


    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter<>(new ImageModel(), this);
    }

    @Override
    protected void loadData() {
        showLoading();
        mPresenter.loadData(URLS.IMAGES_URL, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }


    @Override
    public void showData(ArrayList<Image> images, boolean isRefresh) {

        showContent();

        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
        if (isRefresh) {
            imageCommonAdapter.setData(images);
        } else {
            imageCommonAdapter.appendData(images);
        }

        imageCommonAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPresenter.loadData(URLS.IMAGES_URL, true);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mPresenter.loadData(URLS.IMAGES_URL, false);
        return true;
    }
}

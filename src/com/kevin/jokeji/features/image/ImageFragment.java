package com.kevin.jokeji.features.image;


import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.beans.Image;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class ImageFragment extends BaseFragment implements BaseView<ArrayList<Image>>
        , BGARefreshLayout.BGARefreshLayoutDelegate, AbsListView.OnScrollListener {

    private ListView mListView;
    private CommonPresenter<ArrayList<Image>> mPresenter;
    private ImageAdapter imageCommonAdapter;
    private BGARefreshLayout mRefreshLayout;


    public ImageFragment() {
    }

    @Override
    protected void initView() {
        mListView = findViewById(R.id.list_view);
        mRefreshLayout = findViewById(R.id.pull_to_refresh);
        mListView.setAdapter(imageCommonAdapter = new ImageAdapter(getActivity(), null));

        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.drawable.logo);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_refreshing);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
        mListView.setOnScrollListener(this);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                imageCommonAdapter.setScrollStatue(false);

                int count = view.getChildCount();
                for (int i = 0; i < count; i++) {

                    final Image item = (Image) view.getChildAt(i).getTag(R.id.imageloader_uri);

                    if (item.isImage()) {
                        final ImageView imageView = (ImageView) view.getChildAt(i).findViewById(R.id.img);
                        ImageView icon = (ImageView) view.getChildAt(i).findViewById(R.id.icon);
                        String imgTag = (String) imageView.getTag(R.id.imageloader_uri);
                        String iconTag = (String) icon.getTag(R.id.imageloader_uri);
                        ImageUtils.loadImages(getActivity(), item, imageView, icon, imgTag, iconTag);
                    }

                }

                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                imageCommonAdapter.setScrollStatue(true);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                imageCommonAdapter.setScrollStatue(true);
                break;
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}

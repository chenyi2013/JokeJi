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

public class ImageFragment extends BaseFragment implements BaseView<ArrayList<Image>> {

    private ListView mListView;
    private CommonPresenter<ArrayList<Image>> mPresenter;
    private CommonAdapter<Image> imageCommonAdapter;

    public ImageFragment() {
    }

    @Override
    protected void initView() {
        mListView = findViewById(R.id.list_view);
        mListView.setAdapter(imageCommonAdapter = new ImageAdapter(getActivity(), R.layout.image_item, null));


    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter<>(new ImageModel(), this);
    }

    @Override
    protected void loadData() {
        mPresenter.loadData(URLS.IMAGES_URL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }


    @Override
    public void showData(ArrayList<Image> images) {
        imageCommonAdapter.setData(images);
        imageCommonAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError() {

    }
}

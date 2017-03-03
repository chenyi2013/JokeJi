package com.kevin.jokeji.features.saying;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.base.listview.CommonAdapter;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Saying;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class SayingFragment extends BaseFragment implements BaseView<ArrayList<Saying>>, BGARefreshLayout.BGARefreshLayoutDelegate {

    private CommonPresenter<ArrayList<Saying>> presenter;
    private ListView mListView;
    private BGARefreshLayout mRefreshLayout;
    CommonAdapter<Saying> mAdapter;


    public SayingFragment() {
    }


    @Override
    protected void initView() {

        mListView = findViewById(R.id.saying_list);
        mRefreshLayout = findViewById(R.id.pull_to_refresh);

        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.drawable.logo);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_refreshing);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);

        mAdapter = new CommonAdapter<Saying>(getActivity(), R.layout.saying_item, null) {
            @Override
            protected void convert(ViewHolder viewHolder, Saying item, int position) {
                viewHolder.setText(R.id.saying, item.getTitle());
            }
        };

        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        presenter = new CommonPresenter<>(new SayingModel(), this);
    }

    @Override
    protected void loadData() {

        presenter.loadData(URLS.SAYING, true);

    }

    @Override
    protected void setListener() {

        mRefreshLayout.setDelegate(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Saying saying = (Saying) mListView.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), SayingDetailActivity.class);
                intent.putExtra(SayingDetailActivity.URL, saying.getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_saying;
    }


    @Override
    public void showData(ArrayList<Saying> sayings, boolean isRefresh) {
        mRefreshLayout.endRefreshing();
        mRefreshLayout.endLoadingMore();
        if (isRefresh) {
            mAdapter.setData(sayings);
        } else {
            mAdapter.appendData(sayings);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        presenter.loadData(URLS.SAYING, false);
        return true;
    }
}

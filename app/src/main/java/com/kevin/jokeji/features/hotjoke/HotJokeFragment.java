package com.kevin.jokeji.features.hotjoke;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class HotJokeFragment extends BaseFragment implements BaseView<ArrayList<Joke>>
        , OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    public static final String URL = "url";

    private ListView mJokeListView;

    private JokeAdapter mAdapter;

    BGARefreshLayout mRefreshLayout;
    private CommonPresenter<ArrayList<Joke>> mPresenter;

    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CommonPresenter<>(new HotJokeModel(), this);
    }


    protected void initView() {
        mAdapter = new JokeAdapter();

        mJokeListView = findViewById(R.id.joke_list_view);

        mJokeListView.setOnItemClickListener(this);
        mJokeListView.setAdapter(mAdapter);

        mRefreshLayout = findViewById(R.id.pull_to_refresh);


        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.drawable.logo);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_refreshing);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mRefreshLayout.setDelegate(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_new_layout;
    }

    @Override
    protected void loadData() {

        showLoading();
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString(URL);
            mPresenter.loadData(url, true);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
        intent.putExtra(JokeDetailActivity.JOKE, (Joke) mAdapter.getItem(position));
        startActivity(intent);


    }


    @Override
    protected void onRetry() {
        super.onRetry();
        loadData();
    }

    @Override
    public void showData(ArrayList<Joke> jokes, boolean isRefresh) {

        showContent();

        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();

        if (isRefresh) {
            mAdapter.setData(jokes);
        } else {
            mAdapter.bindData(jokes);
        }

        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void showError() {
        showErrorInfo();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (url != null) {
            mPresenter.loadData(url, true);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (url != null) {
            mPresenter.loadData(url, false);
        }
        return true;
    }

    private class ViewHolder {
        TextView title;
        TextView count;
        TextView data;
    }


    class JokeAdapter extends BaseAdapter {

        private ArrayList<Joke> jokes = new ArrayList<Joke>();

        public void bindData(ArrayList<Joke> jokes) {

            if (jokes != null) {
                this.jokes.addAll(jokes);
            }

        }

        public void setData(ArrayList<Joke> jokes) {
            if (jokes != null) {
                this.jokes = jokes;
            }
        }

        @Override
        public int getCount() {
            return jokes.size();
        }

        @Override
        public Object getItem(int position) {
            return jokes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_new_item_layout,
                        parent, false);
                viewHolder.title = (TextView) convertView
                        .findViewById(R.id.title);
                viewHolder.data = (TextView) convertView
                        .findViewById(R.id.data);
                viewHolder.count = (TextView) convertView
                        .findViewById(R.id.count);
                convertView.setTag(viewHolder);
            }

            Joke joke = jokes.get(position);

            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.title.setText(joke.getTitle());
            viewHolder.count.setText(joke.getCount());
            viewHolder.data.setText(joke.getData());

            return convertView;
        }
    }

}

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

import com.kevin.jokeji.JokeDetailActivity;
import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

public class HotJokeFragment extends BaseFragment implements BaseView<ArrayList<Joke>>
        , OnItemClickListener {

    public static final String URL = "url";

    private ListView mJokeListView;

    private JokeAdapter mAdapter;

    PullToRefreshView mRefreshLayout;
    private CommonPresenter<ArrayList<Joke>> mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CommonPresenter<>(new HotJokeModel(), this);
    }


    protected void initView() {
        mAdapter = new JokeAdapter();

        mJokeListView = (ListView) getView().findViewById(R.id.joke_list_view);
        View footer = getActivity().getLayoutInflater().inflate(
                R.layout.more_item, mJokeListView, false);
        mJokeListView.addFooterView(footer);
        mJokeListView.setOnItemClickListener(this);
        mJokeListView.setAdapter(mAdapter);

        mRefreshLayout = (PullToRefreshView) getView()
                .findViewById(R.id.pull_to_refresh);

        mRefreshLayout.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_new_layout;
    }

    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString(URL);
            mRefreshLayout.setRefreshing(true);
            mPresenter.loadData(url);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        if (mAdapter.getCount() == 0) {
            return;
        }

        if (position < mAdapter.getCount() - 1 + mJokeListView.getFooterViewsCount()) {
            Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
            intent.putExtra(JokeDetailActivity.JOKE, (Joke) mAdapter.getItem(position));
            startActivity(intent);
        } else if (position == mAdapter.getCount() - 1
                + mJokeListView.getFooterViewsCount()) {

        }

    }


    @Override
    public void showErrorInfo() {

    }

    @Override
    public void showData(ArrayList<Joke> jokes) {
        mRefreshLayout.setRefreshing(false);
        if (jokes != null) {
            mAdapter.bindData(jokes);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError() {

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

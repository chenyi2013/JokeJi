package com.kevin.jokeji.features.text;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kevin.jokeji.features.hotjoke.JokeDetailActivity;
import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.base.listview.CommonAdapter;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

import java.util.ArrayList;

public class RankFragment extends BaseFragment implements BaseView<ArrayList<Joke>>
        , OnItemClickListener {

    public static final String JOKE = "joke";

    private ListView mJokeListView;
    private CommonPresenter<ArrayList<Joke>> mPresenter;

    private CommonAdapter<Joke> mAdapter;


    protected void initView() {

        mAdapter = new CommonAdapter<Joke>(getActivity(), R.layout.frg_new_item_layout, null) {
            @Override
            protected void convert(com.kevin.jokeji.base.listview.ViewHolder viewHolder, Joke joke, int position) {
                viewHolder.setText(R.id.title, joke.getTitle());
                viewHolder.setText(R.id.count, joke.getCount());
                viewHolder.setText(R.id.data, joke.getData());
            }
        };

        mJokeListView =  findViewById(R.id.joke_list_view);
        mJokeListView.setOnItemClickListener(this);
        mJokeListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter<>(new RankModel(), this);
    }

    protected void loadData() {
        showLoading();
        mPresenter.loadData(URLS.RANK_URL, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_rank_layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
        intent.putExtra(JOKE, mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void showData(ArrayList<Joke> jokes, boolean isRefresh) {
        showContent();
        mAdapter.setData(jokes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }
}



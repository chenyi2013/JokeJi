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
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;

import java.util.ArrayList;

public class SayingFragment extends BaseFragment implements BaseView<ArrayList<Saying>> {

    private CommonPresenter<ArrayList<Saying>> presenter;
    private ListView mListView;


    public SayingFragment() {
    }


    @Override
    protected void initView() {
        mListView = findViewById(R.id.saying_list);
    }

    @Override
    protected void initData() {
        presenter = new CommonPresenter<>(new SayingModel(), this);
    }

    @Override
    protected void loadData() {

        presenter.loadData("https://www.susu19.com/htm/piclist6/");

    }

    @Override
    protected void setListener() {
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
    public void showData(ArrayList<Saying> sayings) {

        mListView.setAdapter(new CommonAdapter<Saying>(getActivity(), android.R.layout.simple_list_item_1, sayings) {
            @Override
            protected void convert(ViewHolder viewHolder, Saying item, int position) {
                viewHolder.setText(android.R.id.text1, item.getTitle());
            }
        });
    }

    @Override
    public void showError() {

    }
}

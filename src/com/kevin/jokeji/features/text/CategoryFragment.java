package com.kevin.jokeji.features.text;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.base.listview.CommonAdapter;
import com.kevin.jokeji.beans.Category;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;
import com.kevin.jokeji.util.ScreenUtil;

import java.util.ArrayList;

public class CategoryFragment extends BaseFragment implements BaseView<ArrayList<Category>>, OnItemClickListener {

    private GridView mCategoryGrid;
    private CommonAdapter<Category> mAdapter;

    private CommonPresenter<ArrayList<Category>> mPresenter;


    @Override
    protected void initView() {

        final int width = (int) (ScreenUtil.getScreenWidth(getActivity()) / (3 * 1.0));

        mAdapter = new CommonAdapter<Category>(getActivity(), R.layout.frg_category_item_layout, null) {
            @Override
            protected void convert(com.kevin.jokeji.base.listview.ViewHolder viewHolder, Category item, int position) {

                TextView textView = viewHolder.getView(R.id.title);

                LayoutParams params = (LayoutParams) textView
                        .getLayoutParams();
                params.width = width;
                params.height = width;

                textView.setText(item.getTitle());
            }
        };
        mCategoryGrid = findViewById(R.id.category_grid_view);
        mCategoryGrid.setOnItemClickListener(this);
        mCategoryGrid.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter<>(new CategoryModel(), this);
    }

    @Override
    protected void loadData() {
        showLoading();
        mPresenter.loadData(URLS.CATEGORY_URL, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_category_layout;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(getActivity(), JokeListActivity.class);
        intent.putExtra(JokeListActivity.CATEGORY, mAdapter.getItem(position)
                .getUrl());
        startActivity(intent);

    }

    @Override
    public void showData(ArrayList<Category> categories, boolean isRefresh) {
        showContent();
        mAdapter.setData(categories);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

}

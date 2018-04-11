package com.kevin.jokeji.features.saying;

import android.graphics.Bitmap;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseActivity;
import com.kevin.jokeji.base.listview.CommonAdapter;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;
import com.kevin.jokeji.util.ScreenUtil;

import java.util.ArrayList;

public class SayingDetailActivity extends BaseActivity implements BaseView<String> {

    public static final String URL = "url";
    private CommonPresenter<String> presenter;
    private TextView content;


    @Override
    protected void loadData() {

        String url = getIntent().getStringExtra(URL);
        if (url != null) {
            showLoading();
            presenter.loadData(url,true);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_saying_detail;
    }

    @Override
    protected void initView() {
        content = (TextView) findViewById(R.id.content);
    }

    @Override
    protected void initData() {
        presenter = new CommonPresenter<>(new SayingDetailModel(), this);
    }

    @Override
    public void showData(final String strings,boolean isRefresh) {

        showContent();
        content.setText(Html.fromHtml(strings));
    }

    @Override
    public void showError() {

        showErrorInfo();

    }

    @Override
    protected void onRetry() {
        super.onRetry();
        loadData();
    }

}

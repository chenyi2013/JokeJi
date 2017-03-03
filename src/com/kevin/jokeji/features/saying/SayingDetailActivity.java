package com.kevin.jokeji.features.saying;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

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

public class SayingDetailActivity extends BaseActivity implements BaseView<ArrayList<String>> {

    public static final String URL = "url";
    private CommonPresenter<ArrayList<String>> presenter;
    private ListView listView;


    @Override
    protected void loadData() {

        String url = getIntent().getStringExtra(URL);
        if (url != null) {
            presenter.loadData(url,true);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_saying_detail;
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.activity_saying_detail);
    }

    @Override
    protected void initData() {
        presenter = new CommonPresenter<>(new SayingDetailModel(), this);
    }

    @Override
    public void showData(final ArrayList<String> strings,boolean isRefresh) {

        listView.setAdapter(new CommonAdapter<String>(this, R.layout.saying_detail_item, strings) {
            @Override
            protected void convert(ViewHolder viewHolder, final String item, int position) {

                final ImageView imageView = viewHolder.getView(R.id.image);
                Glide.with(SayingDetailActivity.this)
                        .load(item)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {


                                int imageWidth = bitmap.getWidth();
                                int imageHeight = bitmap.getHeight();
                                int height = ScreenUtil.getScreenWidth(SayingDetailActivity.this) * imageHeight / imageWidth;
                                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                                para.height = height;
                                imageView.setLayoutParams(para);
                                Glide.with(SayingDetailActivity.this)
                                        .load(item)
                                        .asBitmap()
                                        .into(imageView);

                            }

                        });
            }
        });
    }

    @Override
    public void showError() {

    }

}

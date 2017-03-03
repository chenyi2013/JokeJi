package com.kevin.jokeji.features.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.kevin.jokeji.R;
import com.kevin.jokeji.base.listview.CommonAdapter;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Image;
import com.kevin.jokeji.util.ScreenUtil;
import com.kevin.jokeji.view.GlideRoundTransform;

import java.util.List;

/**
 * Created by kevin on 17/3/2.
 */

public class ImageAdapter extends CommonAdapter<Image> {

    private Context context;

    public ImageAdapter(Context context, int layoutId, List<Image> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }


    @Override
    protected void convert(ViewHolder viewHolder, final Image item, int position) {
        Glide.with(context)
                .load(item.getIcon())
                .asBitmap()
                .placeholder(R.drawable.logo)
                .transform(new GlideRoundTransform(context))
                .error(R.drawable.logo)
                .into((ImageView) viewHolder.getView(R.id.icon));

        viewHolder.setText(R.id.title, item.getTitle());
        viewHolder.setText(R.id.date, item.getDate());
        viewHolder.setText(R.id.author, item.getAuthor());


        final ImageView imageView = viewHolder.getView(R.id.img);
        Glide.with(context)
                .load(item.getImage())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {


                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();
                        int height = ScreenUtil.getScreenWidth((Activity) context) * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        imageView.setLayoutParams(para);
                        Glide.with(context)
                                .load(item.getImage())
                                .asBitmap()
                                .into(imageView);

                    }

                });

    }
}

package com.kevin.jokeji.features.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.kevin.jokeji.R;
import com.kevin.jokeji.beans.Image;
import com.kevin.jokeji.util.ScreenUtil;
import com.kevin.jokeji.view.GlideRoundTransform;

/**
 * Created by kevin on 17/3/7.
 */

public class ImageUtils {

    private Activity activity;

    ImageUtils(Activity activity) {
        this.activity = activity;
    }

    private void loadOriginRatioImage(final Image item, final ImageView imageView) {
        Glide.with(activity)
                .load(item.getImage())
                .asBitmap()
                .placeholder(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {


                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();
                        int height = ScreenUtil.getScreenWidth(activity) * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        imageView.setLayoutParams(para);


                        if (item.isGif()) {
                            Glide.with(activity)

                                    .load(item.getGifImg())
                                    .asGif()
                                    .placeholder(new BitmapDrawable(bitmap))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .crossFade()
                                    .into(imageView);
                        } else {
                            Glide.with(activity)
                                    .load(item.getImage())
                                    .placeholder(R.drawable.ic_default)
                                    .crossFade()
                                    .into(imageView);
                        }
                    }

                });
    }


    public void loadImages(Image item, ImageView imageView
            , ImageView iconView) {
        loadIcon(item, iconView);
        loadImg(item, imageView);
    }

    public void loadImg(Image item, ImageView imageView) {
        String imgTag = (String) imageView.getTag(R.id.imageloader_uri);
        if (!"1".equals(imgTag)) {
            //设置为已加载过数据
            imageView.setTag(R.id.imageloader_uri, "1");
            loadOriginRatioImage(item, imageView);
        }
    }

    public void loadIcon(Image item, ImageView iconView) {
        String iconTag = (String) iconView.getTag(R.id.imageloader_uri);
        if (!"1".equals(iconTag)) {
            //设置为已加载过数据
            iconView.setTag(R.id.imageloader_uri, "1");
            Glide.with(activity)
                    .load(item.getIcon())
                    .asBitmap()
                    .placeholder(R.drawable.ic_default)
                    .transform(new GlideRoundTransform(activity))
                    .error(R.drawable.ic_default)
                    .into(iconView);
        }
    }

    public void loadDefaultImages(Image item, ImageView imageView, ImageView iconView) {
        loadDefaultImg(item, imageView);
        loadDefaultIcon(item, iconView);
    }

    public void loadDefaultImg(Image item, ImageView imageView) {
        imageView.setTag(R.id.imageloader_uri, item.getImage());
        Glide.with(activity)
                .load(R.drawable.ic_default)
                .crossFade()
                .into(imageView);
    }

    public void loadDefaultIcon(Image item, ImageView iconView) {
        iconView.setTag(R.id.imageloader_uri, item.getIcon());
        Glide.with(activity)
                .load(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .transform(new GlideRoundTransform(activity))
                .crossFade()
                .into(iconView);
    }


    public void loadImageJokes(AbsListView view) {

        int count = view.getChildCount();
        for (int i = 0; i < count; i++) {

            final Image item = (Image) view.getChildAt(i).getTag(R.id.imageloader_uri);

            ImageView icon = (ImageView) view.getChildAt(i).findViewById(R.id.icon);

            if (item.isImage()) {
                final ImageView imageView = (ImageView) view.getChildAt(i).findViewById(R.id.img);
                loadImages(item, imageView, icon);
            } else {
                loadIcon(item, icon);
            }
        }
    }

}

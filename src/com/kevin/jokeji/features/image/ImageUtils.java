package com.kevin.jokeji.features.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
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

    private static void loadOriginRatioImage(final Activity activity, final Image item, final ImageView imageView) {
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


    public static void loadImages(Context context, Image item, ImageView imageView
            , ImageView iconView, String imgTag, String iconTag) {

        if (!"1".equals(iconTag)) {
            //设置为已加载过数据
            iconView.setTag(R.id.imageloader_uri, "1");
            Glide.with(context)
                    .load(item.getIcon())
                    .asBitmap()
                    .placeholder(R.drawable.ic_default)
                    .transform(new GlideRoundTransform(context))
                    .error(R.drawable.ic_default)
                    .into(iconView);
        }

        if (!"1".equals(imgTag)) {
            //设置为已加载过数据
            imageView.setTag(R.id.imageloader_uri, "1");
            loadOriginRatioImage((Activity) context, item, imageView);
        }
    }

}

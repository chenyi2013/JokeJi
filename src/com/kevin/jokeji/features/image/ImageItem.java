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
import com.kevin.jokeji.base.listview.ItemViewDelegate;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Image;
import com.kevin.jokeji.util.ScreenUtil;
import com.kevin.jokeji.view.GlideRoundTransform;

/**
 * Created by kevin on 17/3/6.
 */

public class ImageItem implements ItemViewDelegate<Image> {
    private Context context;
    private boolean isScrollState;

    ImageItem(Context context) {
        this.context = context;
    }

    public void setScrollState(boolean isScrollState) {
        this.isScrollState = isScrollState;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.image_item;
    }

    @Override
    public boolean isForViewType(Image item, int position) {
        return item.isImage();
    }

    @Override
    public void convert(ViewHolder viewHolder, final Image item, final int position) {

        viewHolder.setText(R.id.title, item.getTitle());
        viewHolder.setText(R.id.date, item.getDate());
        viewHolder.setText(R.id.author, item.getAuthor());

        viewHolder.getConvertView().setTag(R.id.imageloader_uri, item);

        final ImageView imageView = viewHolder.getView(R.id.img);
        final ImageView iconView = viewHolder.getView(R.id.icon);

        if (!isScrollState) {


            String imgTag = (String) imageView.getTag(R.id.imageloader_uri);
            String iconTag = (String) iconView.getTag(R.id.imageloader_uri);

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
                Glide.with(context)
                        .load(item.getImage())
                        .asBitmap()
                        .placeholder(R.drawable.ic_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {

                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {


                                int imageWidth = bitmap.getWidth();
                                int imageHeight = bitmap.getHeight();
                                int height = ScreenUtil.getScreenWidth((Activity) context) * imageHeight / imageWidth;
                                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                                para.height = height;
                                imageView.setLayoutParams(para);


                                if (item.isGif()) {
                                    Glide.with(context)

                                            .load(item.getGifImg())
                                            .asGif()
                                            .placeholder(new BitmapDrawable(bitmap))
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .crossFade()
                                            .into(imageView);
                                } else {
                                    Glide.with(context)
                                            .load(item.getImage())
                                            .placeholder(R.drawable.ic_default)
                                            .crossFade()
                                            .into(imageView);
                                }
                            }


                        });
            }

        } else {
            imageView.setTag(R.id.imageloader_uri, item.getImage());
            iconView.setTag(R.id.imageloader_uri, item.getIcon());
            Glide.with(context)
                    .load(R.drawable.ic_default)
                    .crossFade()
                    .into(imageView);
            Glide.with(context)
                    .load(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .transform(new GlideRoundTransform(context))
                    .crossFade()
                    .into(iconView);
        }


    }

}

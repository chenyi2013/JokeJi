package com.kevin.jokeji.features.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kevin.jokeji.R;
import com.kevin.jokeji.base.listview.ItemViewDelegate;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Image;
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
            ImageUtils.loadImages(context, item, imageView, iconView, imgTag, iconTag);

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

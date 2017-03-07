package com.kevin.jokeji.features.image;

import android.content.Context;
import android.widget.ImageView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.listview.ItemViewDelegate;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Image;

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
            ImageUtils.loadImages(context, item, imageView, iconView);
        } else {
            ImageUtils.loadDefaultImages(context, item, imageView, iconView);
        }


    }


}

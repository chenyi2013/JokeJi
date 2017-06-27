package com.kevin.jokeji.features.image;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.listview.ItemViewDelegate;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Image;

/**
 * Created by kevin on 17/3/6.
 */

public class TextItem implements ItemViewDelegate<Image> {

    private boolean isScrollState;
    private ImageUtils imageUtils;

    public void setScrollState(boolean isScrollState) {
        this.isScrollState = isScrollState;
    }


    TextItem(Context context) {
        imageUtils = new ImageUtils((Activity) context);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.text_item;
    }

    @Override
    public boolean isForViewType(Image item, int position) {
        return !item.isImage();
    }

    @Override
    public void convert(ViewHolder holder, Image image, int position) {

        holder.getConvertView().setTag(R.id.imageloader_uri, image);

        ImageView icon = holder.getView(R.id.icon);
        if (!isScrollState) {
            imageUtils.loadIcon(image, icon);
        } else {
            imageUtils.loadDefaultIcon(image, icon);
        }

        holder.setText(R.id.text, Html.fromHtml(image.getContent()));
        holder.setText(R.id.title, image.getTitle());
        holder.setText(R.id.date, image.getDate());
        holder.setText(R.id.author, image.getAuthor());

    }
}

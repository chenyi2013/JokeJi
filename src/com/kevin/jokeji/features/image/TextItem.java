package com.kevin.jokeji.features.image;

import android.content.Context;
import android.text.Html;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.listview.ItemViewDelegate;
import com.kevin.jokeji.base.listview.ViewHolder;
import com.kevin.jokeji.beans.Image;

/**
 * Created by kevin on 17/3/6.
 */

public class TextItem implements ItemViewDelegate<Image> {

    private Context context;

    TextItem(Context context) {
        this.context = context;
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
        holder.getConvertView().setTag(R.id.imageloader_uri,image);
        holder.setText(R.id.text, Html.fromHtml(image.getContent()));
    }
}

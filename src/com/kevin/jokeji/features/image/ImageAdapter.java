package com.kevin.jokeji.features.image;

import android.content.Context;

import com.kevin.jokeji.base.listview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by kevin on 17/3/2.
 */

public class ImageAdapter extends MultiItemTypeAdapter {

    private boolean isScrollStatue;
    private ImageItem imageItem;
    private TextItem textItem;

    public void setScrollStatue(boolean isScrollStatue) {
        this.isScrollStatue = isScrollStatue;
        imageItem.setScrollState(isScrollStatue);
    }

    public ImageAdapter(Context context, List datas) {
        super(context, datas);
        imageItem = new ImageItem(context);
        textItem = new TextItem(context);
        addItemViewDelegate(imageItem);
        addItemViewDelegate(textItem);
    }
}



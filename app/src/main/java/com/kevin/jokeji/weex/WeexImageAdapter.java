package com.kevin.jokeji.weex;

import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by kevin on 17/3/9.
 */

public class WeexImageAdapter implements IWXImgLoaderAdapter {
    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {

        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }
}

package com.kevin.jokeji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.kevin.jokeji.R;

/**
 * Created by kevin on 15/9/26.
 */
public class CustomProgressBar extends ProgressBar {
    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Bitmap drawable = BitmapFactory.decodeResource(getResources(), R.drawable.timg1);
        setMeasuredDimension(drawable.getWidth(), drawable.getHeight());
        drawable.recycle();
    }
}

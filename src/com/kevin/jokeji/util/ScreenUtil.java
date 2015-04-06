package com.kevin.jokeji.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtil {

	public static int getScreenWidth(Activity activity) {

		DisplayMetrics metric = new DisplayMetrics();

		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // ÆÁÄ»¿í¶È£¨ÏñËØ£©
		return width;

	}

}

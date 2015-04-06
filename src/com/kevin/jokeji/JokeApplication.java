package com.kevin.jokeji;

import java.io.File;
import java.io.IOException;

import android.app.Application;

import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.Config;

public class JokeApplication extends Application {

	private DiskLruCache mDiskLruCache;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public synchronized DiskLruCache getDiskLruCache() {

		try {

			File cacheDir = CacheHelper.getDiskCacheDir(this, Config.CACHE_DIR);
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}

			mDiskLruCache = DiskLruCache.open(cacheDir,
					CacheHelper.getAppVersion(this), 1, Config.CACHE_SIZE);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mDiskLruCache;
	}

}

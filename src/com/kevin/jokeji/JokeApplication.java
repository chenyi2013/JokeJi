package com.kevin.jokeji;

import android.app.Application;

import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.Config;

import java.io.File;
import java.io.IOException;

public class JokeApplication extends Application {

    private static DiskLruCache mDiskLruCache;
    private static JokeApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static synchronized DiskLruCache getDiskLruCache() {

        try {

            File cacheDir = CacheHelper.getDiskCacheDir(application, Config.CACHE_DIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            mDiskLruCache = DiskLruCache.open(cacheDir,
                    CacheHelper.getAppVersion(application), 1, Config.CACHE_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mDiskLruCache;
    }

}

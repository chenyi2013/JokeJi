package com.kevin.jokeji.app;

import android.app.Application;

import com.kevin.jokeji.cache.CacheHelper;
import com.kevin.jokeji.cache.DiskLruCache;
import com.kevin.jokeji.config.Config;
import com.kevin.jokeji.weex.NetworkAdapter;
import com.kevin.jokeji.weex.WeexImageAdapter;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

import java.io.File;
import java.io.IOException;

public class JokeApplication extends Application {

    private static DiskLruCache mDiskLruCache;
    private static JokeApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        InitConfig config = new InitConfig.Builder()
                .setImgAdapter(new WeexImageAdapter())
//                .setHttpAdapter(new NetworkAdapter())
                .build();
        WXSDKEngine.initialize(this, config);
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

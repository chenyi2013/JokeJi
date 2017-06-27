package com.kevin.jokeji.cache;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CacheHelper {

    /**
     * 得到缓存目录
     *
     * @param context
     * @param uniqueName
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 得到应用版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static <T> void saveObjectToDisk(DiskLruCache diskLruCache,
                                            String url, T t) {

        String key = CacheHelper.hashKeyForDisk(url);
        DiskLruCache.Editor editor;

        try {

            editor = diskLruCache.edit(key);

            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                ObjectOutputStream objectStream = new ObjectOutputStream(
                        outputStream);
                objectStream.writeObject(t);
                objectStream.flush();
                editor.commit();

                objectStream.close();
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public static <T> T getObjectToDisk(DiskLruCache diskLruCache, String url) {

        try {

            T t = null;
            String key = CacheHelper.hashKeyForDisk(url);
            DiskLruCache.Snapshot snapShot = diskLruCache.get(key);

            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                ObjectInputStream objectIn = new ObjectInputStream(is);
                t = (T) objectIn.readObject();
                is.close();
                objectIn.close();

                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

}

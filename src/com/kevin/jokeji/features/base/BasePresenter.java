package com.kevin.jokeji.features.base;

/**
 * Created by kevin on 17/3/2.
 */

public interface BasePresenter<T> {

    void loadData(String url);

    void onGetData(T t);

    void onError(Exception exception);
}

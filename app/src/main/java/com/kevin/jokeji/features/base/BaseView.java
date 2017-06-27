package com.kevin.jokeji.features.base;

/**
 * Created by kevin on 17/3/2.
 */

public interface BaseView<T> {

    void showData(T t,boolean isRefresh);
    void showError();
}

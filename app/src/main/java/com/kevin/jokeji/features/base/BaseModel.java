package com.kevin.jokeji.features.base;

/**
 * Created by kevin on 17/3/2.
 */

public interface BaseModel<T> {

    void loadData(String url,boolean isRefresh);
    void setPresenter(BasePresenter<T> presenter);
}

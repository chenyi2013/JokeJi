package com.kevin.jokeji.features.base;

/**
 * Created by kevin on 17/3/2.
 */

public interface BaseModel<T> {

    void loadData(String url);

    T getData(String ulr);

    void setPresenter(BasePresenter<T> presenter);
}

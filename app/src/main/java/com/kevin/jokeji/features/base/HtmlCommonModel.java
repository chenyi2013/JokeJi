package com.kevin.jokeji.features.base;

import com.kevin.jokeji.app.JokeApplication;
import com.kevin.jokeji.cache.CacheHelper;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kevin on 17/3/2.
 */

public abstract class HtmlCommonModel<T> implements BaseModel<T> {

    protected BasePresenter<T> commonPresenter;
    private int page = 1;

    @Override
    public void setPresenter(BasePresenter<T> presenter) {
        this.commonPresenter = presenter;
    }

    public abstract T getData(String url);


    protected String formatUrlForPageId(String url, int page) {
        return url;
    }

    protected boolean isUseCache() {
        return false;
    }


    @Override
    final public void loadData(String url, final boolean isRefresh) {
        try {

            if (isRefresh) {
                page = 1;
            }

            if (isUseCache()) {
                T data = (T) CacheHelper.getObjectToDisk(JokeApplication.getDiskLruCache(), url);

                if (data != null) {
                    if (data instanceof ArrayList) {
                        ArrayList list = (ArrayList) data;
                        if (list.size() > 0) {
                            commonPresenter.onGetData(data, isRefresh);
                            return;
                        }
                    }
                }
            }


            Observable
                    .just(formatUrlForPageId(url, page))
                    .map(new Func1<String, T>() {
                        @Override
                        public T call(String url) {
                            T result = getData(url);
                            if (result != null) {
                                CacheHelper.saveObjectToDisk(JokeApplication
                                        .getDiskLruCache(), url, result);
                            }
                            return result;
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<T>() {
                        @Override
                        public void call(T t) {
                            page++;
                            commonPresenter.onGetData(t, isRefresh);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            commonPresenter.onError(new Exception());
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
            commonPresenter.onError(e);
        }

    }


}

package com.kevin.jokeji.features.base;

import com.kevin.jokeji.JokeApplication;
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

    @Override
    public void setPresenter(BasePresenter<T> presenter) {
        this.commonPresenter = presenter;
    }

    public abstract T getData(String url);


    final public void loadData(String url) {
        try {

//            T data = (T) CacheHelper.getObjectToDisk(JokeApplication.getDiskLruCache(), url);
//
//            if (data != null) {
//                if (data instanceof ArrayList) {
//                    ArrayList list = (ArrayList) data;
//                    if (list.size() > 0) {
//                        commonPresenter.onGetData(data);
//                        return;
//                    }
//                }
//            }


            Observable
                    .just(url)
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
                            commonPresenter.onGetData(t);
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
            commonPresenter.onError(e);
        }

    }


}

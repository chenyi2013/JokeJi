package com.kevin.jokeji.network.retrofit;


import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {


    public BaseSubscriber() {
    }

    @Override
    final public void onError(java.lang.Throwable e) {
        if(e instanceof Throwable){
            onError((Throwable)e);
        } else {
            onError(new Throwable(e));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

}

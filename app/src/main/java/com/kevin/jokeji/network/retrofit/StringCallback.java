package com.kevin.jokeji.network.retrofit;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by kevin on 17/1/3.
 */

public class StringCallback extends ApiCallback<ResponseBody> {

    private HttpEngine.ResponseCallBack<String> callBack;


    public StringCallback(HttpEngine.ResponseCallBack<String> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        callBack.onStart();
    }

    @Override
    public void onSuccess(ResponseBody model) {
        try {
            callBack.onSuccess(model.string());
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onError(e);
        }

    }

    @Override
    public void onFailure(String msg) {
        callBack.onError(new RuntimeException(msg));

    }

    @Override
    public void onFinish() {
        callBack.onCompleted();

    }
}

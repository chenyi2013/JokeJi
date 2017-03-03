package com.kevin.jokeji.network.retrofit;


import com.kevin.jokeji.util.Utils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

import static com.kevin.jokeji.util.JsonUtil.parseObject;


/**
 * Created by kevin on 16/12/30.
 */

public class JsonCallback<T> extends ApiCallback<ResponseBody> {


    private Type type;
    private HttpEngine.ResponseCallBack<T> callBack;

    public JsonCallback(HttpEngine.ResponseCallBack<T> callBack) {

        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (Utils.MethodHandler(types) == null || Utils.MethodHandler(types).size() == 0) {
            onFailure("获取实体类型失败");
            return;
        }
        type = Utils.MethodHandler(types).get(0);
        this.callBack = callBack;

    }

    @Override
    public void onSuccess(ResponseBody model) {
        try {
            if (model == null) {
                onError(new RuntimeException("服务器返回的数据格式异常"));
                onFinish();
                return;
            } else {

                String data = model.string();

                if (data == null) {
                    onError(new RuntimeException("服务器返回的数据格式异常"));
                    onFinish();
                    return;
                }

                this.callBack.onSuccess(
                        (T) parseObject(data, type));
                onFinish();
            }

        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
            onFinish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        callBack.onStart();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        callBack.onError(e);
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

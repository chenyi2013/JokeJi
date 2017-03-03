package com.kevin.jokeji.network.retrofit;

import com.alibaba.fastjson.JSON;
import com.kevin.jokeji.BuildConfig;
import com.kevin.jokeji.util.Utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HttpEngine {

    private static final String TAG = "AppClient";
    public static Retrofit mRetrofit;
    CompositeSubscription mCompositeSubscription;


    public HttpEngine() {
        mCompositeSubscription = new CompositeSubscription();
    }

    public static synchronized Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();


            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
                builder.addInterceptor(new BaseInterceptor(getHeader()));
            }
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    public static HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8;");
        return header;
    }


    public void post(String url, Map<String, String> param, ResponseCallBack<String> subscriber) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executePost(url, param)
                .compose(schedulersTransformer)
                .subscribe(new StringCallback(subscriber));
        mCompositeSubscription.add(subscribe);

    }

    public <T> void executePost(final String url, Map<String, String> param, final ResponseCallBack<T> callBack) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executePost(url, param)
                .compose(schedulersTransformer)
                .subscribe(new JsonCallback<T>(callBack));
        mCompositeSubscription.add(subscribe);
    }


    public void get(String url, Map<String, String> param, ResponseCallBack<String> subscriber) {
        Subscription subscribe = retrofit().create(ApiStores.class)
                .executeGet(url, param)
                .compose(schedulersTransformer)
                .subscribe(new StringCallback(subscriber));
        mCompositeSubscription.add(subscribe);

    }


    public <T> void executeGet(final String url, Map<String, String> param, final ResponseCallBack<T> callBack) {

        Subscription subscribe = retrofit().create(ApiStores.class)
                .executeGet(url, param)
                .compose(schedulersTransformer)
                .subscribe(new JsonCallback<T>(callBack));

        mCompositeSubscription.add(subscribe);
    }


    /**
     * post Json 数据到服务器并返回字串数据
     *
     * @param url
     * @param param
     * @param subscriber
     */
    public void json(String url, Object param, ResponseCallBack<String> subscriber) {

        String jsonStr = JSON.toJSONString(param);
        Subscription subscribe = retrofit().create(ApiStores.class)
                .postJson(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .subscribe(new StringCallback(subscriber));
        mCompositeSubscription.add(subscribe);

    }


    /**
     * post Json 数据到服务器并返回T类型的数据
     *
     * @param url
     * @param param
     * @param callBack
     * @param <T>
     */
    public <T> void executeJson(final String url, Object param, final ResponseCallBack<T> callBack) {

        String jsonStr = JSON.toJSONString(param);

        Subscription subscribe = retrofit().create(ApiStores.class)
                .postJson(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .subscribe(new JsonCallback<T>(callBack));

        mCompositeSubscription.add(subscribe);
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    private static Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


    /**
     * ResponseCallBack <T> Support your custom data model
     */
    public static interface ResponseCallBack<T> {

        public void onStart();

        public void onCompleted();

        public abstract void onError(Throwable e);

        public abstract void onSuccess(T response);

    }


}

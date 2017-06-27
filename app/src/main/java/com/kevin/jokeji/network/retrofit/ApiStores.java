package com.kevin.jokeji.network.retrofit;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiStores {

    String API_SERVER_URL = "https://www.baidu.com";

    @POST()
    Observable<ResponseBody> postJson(
            @Url() String url,
            @Body RequestBody jsonBody);


    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> executePost(
            @Url() String url,
            @FieldMap Map<String, String> maps);


    @GET()
    Observable<ResponseBody> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps);

}

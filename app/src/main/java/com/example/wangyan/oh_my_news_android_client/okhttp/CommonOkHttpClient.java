package com.example.wangyan.oh_my_news_android_client.okhttp;

import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.response.CommonCallBack;
import com.example.wangyan.oh_my_news_android_client.okhttp.response.CommonFileCallBack;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wangyan on 2017/4/27.
 */

public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient okHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        okHttpClient = okHttpClientBuilder.build();
    }

    public static void post(Request request, ResponseDataHandle handle){
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonCallBack(handle));

    }
    public static void downloadFile(Request request, ResponseDataHandle handle)
    {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonFileCallBack(handle));
    }
}

package com.example.wangyan.oh_my_news_android_client.util;

import android.os.Message;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by fanfan on 2017/3/28.
 */

public class SendOkHttpRequestPost {
    public SendOkHttpRequestPost(String address, okhttp3.Callback callback, RequestBody requestBody) {
        OkHttpClient okHttpClient=new OkHttpClient();
        final Message message=new Message();

        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);

    }
}

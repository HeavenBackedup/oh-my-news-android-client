package com.example.wangyan.oh_my_news_android_client;

import android.os.Message;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by fanfan on 2017/3/28.
 */

public class SendOkHttpRequestGet {

    public SendOkHttpRequestGet(String address, okhttp3.Callback callback) {
        OkHttpClient okHttpClient=new OkHttpClient();
        final Message message=new Message();


        Request request=new Request.Builder()
                .url(address)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }


}

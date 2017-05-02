package com.example.wangyan.oh_my_news_android_client.okhttp.listener;

/**
 * Created by wangyan on 2017/4/27.
 */

public interface ResponseDataListener {

    public void onSuccess(Object responseObj);
    public void onFailure(Object reasonObj);
}

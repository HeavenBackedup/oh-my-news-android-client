package com.example.wangyan.oh_my_news_android_client.okhttp.listener;

/**
 * Created by wangyan on 2017/4/29.
 */
  //真正处理一个response
public class ResponseDataHandle {
    public ResponseDataListener responseDataListener = null;

    public ResponseDataHandle(ResponseDataListener responseDataListener) {
        this.responseDataListener = responseDataListener;
    }
}

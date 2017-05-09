package com.example.wangyan.oh_my_news_android_client.okhttp.listener;

/**
 * Created by wangyan on 2017/5/6.
 */

public interface ResponseDownloadListener extends ResponseDataListener{
    public void onProgress(int progress);
}

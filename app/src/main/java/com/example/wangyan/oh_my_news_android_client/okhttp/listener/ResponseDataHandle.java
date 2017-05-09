package com.example.wangyan.oh_my_news_android_client.okhttp.listener;

import android.os.Environment;

/**
 * Created by wangyan on 2017/4/29.
 */
  //真正处理一个response
public class ResponseDataHandle {
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+System.currentTimeMillis()+".jpg";
    public ResponseDownloadListener responseDownloadListener;
    public ResponseDataListener responseDataListener = null;
    public String fileSourse;

    public ResponseDataHandle(ResponseDownloadListener responseDownloadListener) {
        this.responseDownloadListener = responseDownloadListener;
    }

    public ResponseDataHandle(ResponseDownloadListener responseDownloadListener, String fileSourse) {
        this.responseDownloadListener = responseDownloadListener;
        this.fileSourse = fileSourse;
    }

    public ResponseDataHandle(ResponseDataListener responseDataListener) {
        this.responseDataListener = responseDataListener;
    }
}

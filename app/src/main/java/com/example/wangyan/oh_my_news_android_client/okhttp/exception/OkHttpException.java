package com.example.wangyan.oh_my_news_android_client.okhttp.exception;

/**
 * Created by wangyan on 2017/4/29.
 */

public class OkHttpException extends Exception {
    private int ecode;
    private Object emsg;

    public OkHttpException() {
    }

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}

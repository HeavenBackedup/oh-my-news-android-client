package com.example.wangyan.oh_my_news_android_client.util;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by fanfan on 2017/5/10.
 */

public class RequestBodyForm {
    private Object params;
    private String url;

    public RequestBodyForm(String url, Object params) {
        this.params = params;
        this.url=url;
    }

    public RequestBodyForm(String url) {
        this.url = url;
    }

    public RequestBody getRequestBody(){
        Gson gson = new Gson();
        String updateParams = gson.toJson(params);
        Log.i("fanfan",updateParams);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody= RequestBody.create(JSON,updateParams);
        return requestBody;
    }

    public String getUrl(){
        String updateUrl = "http://114.215.97.149:8080/oh-my-news"+url+".do";
        return updateUrl;
    }

}

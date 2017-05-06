package com.example.wangyan.oh_my_news_android_client.okhttp.request;

/**
 * Created by wangyan on 2017/4/29.
 */

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @founction负责创建各种各样的请求对象，包括post和文件上传和文件下载
 */

public class CommonRequest {
    public static Request createGetResquest(String url){
        return new Request.Builder().url(url).build();
    }
    public static Request createPostResquest(String url, Object params){
        Gson gson = new Gson();
        String updateParams = gson.toJson(params);
        Log.i("wangyan",updateParams);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body= RequestBody.create(JSON,updateParams);

        String updateUrl = "http://114.215.97.149:8080/oh-my-news"+url+".do";

//        if (params != null){
//            for (Map.Entry<String,String> entry: params.stringParams.entrySet()){
//                builder.add(entry.getKey(),entry.getValue());
//            }
//        }
        Request request = new Request.Builder()
                .url(updateUrl)
                .post(body)
                .build();
        Log.i("wangyan",updateUrl);
        return request;
    }

//    /**
//     * 图片上传
//     * @param url
//     * @param params
//     * @return
//     */
//    public static Request createMultiPostResquest(String url,RequestParams params){
//
//    }


}

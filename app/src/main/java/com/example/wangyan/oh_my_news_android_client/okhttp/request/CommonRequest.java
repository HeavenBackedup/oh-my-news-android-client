package com.example.wangyan.oh_my_news_android_client.okhttp.request;

/**
 * Created by wangyan on 2017/4/29.
 */

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * @founction负责创建各种各样的请求对象，包括post和文件上传和文件下载
 */

public class CommonRequest {
    public static Request createPostResquest(String url, RequestParams params){
        Gson gson = new Gson();
        String updateParams = gson.toJson(params);
        Log.i("wangyan",updateParams);
        String updateUrl = "http://localhost:7777/oh-my-news"+url+".do";
        FormBody.Builder builder = new FormBody.Builder();
        if (updateParams != null){
            builder.add("params",updateParams);
        }
//        if (params != null){
//            for (Map.Entry<String,String> entry: params.stringParams.entrySet()){
//                builder.add(entry.getKey(),entry.getValue());
//            }
//        }
        FormBody eFormBody =  builder.build();
        Request request = new Request.Builder()
                .url(updateUrl)
                .post(eFormBody)
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

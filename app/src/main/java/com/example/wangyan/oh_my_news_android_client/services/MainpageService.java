package com.example.wangyan.oh_my_news_android_client.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;

import java.util.HashMap;
import java.util.Map;

public class MainpageService extends Service{
//    public static final String NEWS_ID="news_id";
    public static final String NEWS_IMGS="news_imgs";
    public static final String NEWS_TITLE="news_title";
    public static final String NEWS_AUTHOR = "news_author";
    private Callback callback;
    private boolean isExecute = true;



    public MainpageService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        responseNewsData();
        return super.onStartCommand(intent, flags, startId);
    }

    //    public static Queue<> tasks = new PriorityQueue<Task>() ;
    @Override
    public void onCreate() {

        super.onCreate();
//        请求首页数据刷新页面
        new Thread(new Runnable() {
            @Override
            public void run() {
                responseNewsData();
                stopSelf();
            }

        }).start();

    }
    private void responseImg(Object responseObj){
        final Object responseObj1 = responseObj;
        String url_img = "http://cms-bucket.nosdn.127.net/catchpic/e/e8/e8af197c3b3ab1786ef430976c9ae8f3.jpg?imageView&thumbnail=550x0";
        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url_img),new ResponseDataHandle(new ResponseDownloadListener() {
            @Override
            public void onProgress(int progress) {
            }
            @Override
            public void onSuccess(Object responseObj2) {
                if (callback != null){
//                    callback.onDataChange(JsonToObject.getNews(responseObj1,responseObj2));
                }
            }
            @Override
            public void onFailure(Object reasonObj) {
            }
        }));
    }
    private void responseNewsData(){
        String url = "/mainpage/showPage";
        Map<String,Object> map = new HashMap<String, Object>();
        int pageIndex = 0;
        int currentPage = 1;
        map.put("pageIndex",pageIndex);
        map.put("currentPage",currentPage);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,map),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                responseImg(responseObj);
            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }
    public class Binder extends android.os.Binder{
        public MainpageService getMainpageService(){
            return MainpageService.this;
        }
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    public static interface Callback{
        void onDataChange(Object data);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

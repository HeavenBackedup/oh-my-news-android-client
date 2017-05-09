package com.example.wangyan.oh_my_news_android_client.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.wangyan.oh_my_news_android_client.Bean.User;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.MainpageConnection;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class RegistService extends Service {
    private Callback callback;
    private String username;
    private String pwd;

    public RegistService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        username = intent.getStringExtra("username");
        pwd = intent.getStringExtra("pwd");
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //验证用户名是否已存在
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                responseSubmitData();
              }
            private void responseSubmitData(){
//                Map<String,Object> params = new HashMap<String, Object>();
//                params.put("username",username);
//                params.put("pwd",pwd);
                User params = new User();
                params.setUsername(username);
                params.setPassword(pwd);
                Log.i("yan",username+".................123445..............."+pwd);
//                       String urlSubmit = "/register/androidSubmitInfo";
                String urlSubmit = "/register/submitInfo";

                CommonOkHttpClient.post(CommonRequest.createPostResquest(urlSubmit,params),new ResponseDataHandle(new ResponseDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        Log.i("yan",responseObj.toString()+"........................");
                        if (callback != null){
                            callback.onDataChange(responseObj);
                        }
                    }
                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                }));

            }
        }).start();


    }

    public class Binder extends android.os.Binder{
        public RegistService getRegistService(){
            return RegistService.this;
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

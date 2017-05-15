package com.example.wangyan.oh_my_news_android_client.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.wangyan.oh_my_news_android_client.Bean.User;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.JsonToObject;

import java.util.HashMap;
import java.util.Map;

public class LoginService extends Service {
    private Callback callback;
    private String username;
    private String pwd;
    public LoginService() {
    }
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();
        username = intent.getStringExtra("username");
        pwd = intent.getStringExtra("pwd");
        return new Binder();
    }

    public class Binder extends android.os.Binder{
        public LoginService getLoginService(){
            return LoginService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                responseData();
            }
            private void responseData(){
                User params = new User();
                if (username != null && pwd != null){
                    Log.i("yan",username+"..........wangyan..........."+pwd);
                    params.setUsername(username);
                    params.setPassword(pwd);
                    String url = "/login/submitInfo";
                    CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {

                            //将返回的json对象进行解析，只返回给页面具体值
                            if (callback != null){
                                callback.onDataChange(JsonToObject.getLogin(responseObj));
                            }
                        }
                        @Override
                        public void onFailure(Object reasonObj) {
                            Map<String,Object> map = new HashMap<String, Object>();
                            OkHttpException exception = (OkHttpException) reasonObj;
                            if (exception.getEcode() == -1 && exception.getEmsg() == null){
                                map.put("error","error");
                                if (callback != null) {
                                    callback.onDataChange(map);
                                }
                            }

                        }
                    }));
                }
            }
        }).start();
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

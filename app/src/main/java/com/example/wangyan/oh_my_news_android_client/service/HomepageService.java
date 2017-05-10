package com.example.wangyan.oh_my_news_android_client.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomepageService extends Service {
    private Callback callback;
    private int userId;
    private HomepageUserInfo homepageUserInfo;
//    private Object data;
    public HomepageService() {

    }

    @Override
    public void onCreate() {
        Map<String,Object> params = new HashMap<String,Object>();
        String url="/homePage/common";
        params.put("userId",userId);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject=new JSONObject(responseObj.toString());
                    Log.i("responseObj",responseObj.toString()+111);
                    if (jsonObject!=null){
                        homepageUserInfo.setAvatar((String) jsonObject.get("avatarPath"));
                        homepageUserInfo.setUserId((Integer) jsonObject.get("usersId"));
                        homepageUserInfo.setNickname((String) jsonObject.get("nickName"));
                        homepageUserInfo.setSignature((String) jsonObject.get("signature"));
                        homepageUserInfo.setConcerns((Integer) jsonObject.get("fans"));
                        homepageUserInfo.setFans((Integer) jsonObject.get("followers"));
                        Log.i("homepage",homepageUserInfo.toString());
                        if (callback!=null){
                            new Callback(){

                                @Override
                                public void onDataChange(Object data) {
                                    data=homepageUserInfo;
                                }

                            };
                        }
                        stopSelf();
                    }
                } catch (Exception e) {
                    Log.i("exp",e.toString()+111);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        userId=intent.getIntExtra("userId",1);

        return new Binder();

    }


    public class Binder extends android.os.Binder{
        public HomepageService getHomepageService(){
            return HomepageService.this;
        }

    }

    public void setCallback(Callback callback){
        this.callback=callback;
    }

    public static interface Callback{
        void onDataChange(Object data);
}

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


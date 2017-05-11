package com.example.wangyan.oh_my_news_android_client.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailService extends Service {

    int userId;
    int articleId;


    public DetailService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userId=intent.getIntExtra("userId",-1);
        articleId=intent.getIntExtra("articleId",-1);
        String action=intent.getStringExtra("action");
        switch(action){
            case "comment":
                submit("comment",intent.getStringExtra("data"));
                break;
            case "score":
                submit("score",intent.getStringExtra("data"));
                break;
            case "report":
                submit("report",intent.getBooleanExtra("data",false));
                break;
            case "reward":
                submit("reward",intent.getStringExtra("data"));
                break;
            case "like":
                submit("like",intent.getBooleanExtra("data",false));
                break;
            case "collect":
                submit("collect",intent.getBooleanExtra("data",false));
                break;



        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void submit(String type, String msg){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/androidSubmit";

        params.put("articalId",articleId);
        params.put("userId",userId);
        params.put("type",type);

        switch (type){
            case "comment":
                params.put("comment",msg);
                break;
            case "score":
                params.put("score",msg);
                break;
            case "reward":
                params.put("reward",msg);
                break;
        }

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
//                Toast.makeText(CommentActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));
    }
    public void submit(String type, Boolean is){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/androidSubmit";

        params.put("articalId",articleId);
        params.put("userId",userId);
        params.put("type",type);

        switch (type){
            case "report":
                params.put("report",is);
                break;
            case "like":
                params.put("like",is);
                break;
            case "collect":
                params.put("collect",is);
                break;
        }

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
//                Toast.makeText(CommentActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

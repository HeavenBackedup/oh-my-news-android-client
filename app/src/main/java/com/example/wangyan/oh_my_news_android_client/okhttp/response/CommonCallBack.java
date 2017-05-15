package com.example.wangyan.oh_my_news_android_client.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wangyan on 2017/4/29.
 */

public class CommonCallBack implements Callback {
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int OTHER_ERROR = -2; // the unknow error

    private ResponseDataListener responseDataListener = null;
    private Handler handler;

    public CommonCallBack(ResponseDataHandle handle){
        responseDataListener = handle.responseDataListener;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        //返回到主线程中
        for(StackTraceElement s:e.getStackTrace()){
            Log.e("error",s.toString());
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                e.getMessage();
                responseDataListener.onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.i("wangyan ","response");
        Log.i("onResponse",response.toString());
//        final byte[] resultJson=response.body().bytes();
        final String resultJson = response.body().string();
        handler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(resultJson);
            }
        });

    }
    private void handleResponse(Object resultObj){
        if (resultObj == null){
            responseDataListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
        }else {
            try {
                JSONObject jsonObject = new JSONObject(resultObj.toString());
                Object result = jsonObject.get("data");
                Log.i("fan","...."+result.toString());
                responseDataListener.onSuccess(result);
            }catch (Exception e){
                responseDataListener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));

            }
        }


    }

//    private void handleResponse(String result){
//        if (result==null){
//            responseDataListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
//        }else {
//            try {
//                Log.i("string ",result);
//                JSONObject jsonObject=new JSONObject(result);
//                Log.i("json", (String) jsonObject.get("data"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

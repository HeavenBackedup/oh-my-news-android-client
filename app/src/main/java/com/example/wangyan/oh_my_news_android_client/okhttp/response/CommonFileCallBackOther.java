package com.example.wangyan.oh_my_news_android_client.okhttp.response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wangyan on 2017/5/8.
 */

public class CommonFileCallBackOther implements Callback {
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int IO_ERROR = -2; // the unknow error
    /**
     * 将数据转发至UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    private ResponseDownloadListener rListener;
    private Handler handler;
    private int progress;
    private Bitmap bitmap;

    public CommonFileCallBackOther(ResponseDataHandle handle) {
        this.rListener = handle.responseDownloadListener;
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case PROGRESS_MESSAGE:
                        rListener.onProgress((int)msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                rListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        bitmap = handleResponse(response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null){
                    rListener.onSuccess(bitmap);
                }else{
                    rListener.onFailure(new OkHttpException(IO_ERROR,EMPTY_MSG));
                }
            }
        });
    }
    private Bitmap handleResponse(Response response){
        if (response == null) {
            return null;
        }
        InputStream inputStream = response.body().byteStream() ;
        bitmap = BitmapFactory.decodeStream(inputStream);
        byte[] buffer = new byte[2048];
        int length = -1;
        int currentLength = 0;
        double sumLength = 0;
        try {
            sumLength = (double) response.body().contentLength();
            while ((length = inputStream.read(buffer)) != -1) {
                currentLength += length;
                progress = (int) (currentLength / sumLength * 100);
                handler.obtainMessage(PROGRESS_MESSAGE,progress).sendToTarget();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}

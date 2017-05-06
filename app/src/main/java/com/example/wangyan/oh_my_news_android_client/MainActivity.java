package com.example.wangyan.oh_my_news_android_client;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle.PATH;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private Button button2;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        button = (Button)findViewById(R.id.BTN_content);
        editText = (EditText)findViewById(R.id.ET_content);
        button2 = (Button)findViewById(R.id.BTN_img);
        imageView = (ImageView) findViewById(R.id.IV_img);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    downloadFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void postRequest(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/accountManage/getUser";

        params.put("userId","123");

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                String email;
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    if (jsonObject.has("email")) {
                        email = jsonObject.getString("email");
                        editText.setText(email);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：其他错误）

                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(MainActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(MainActivity.this,"数据格式有误",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
    private void downloadFile()throws FileNotFoundException {
        String url = "http://cms-bucket.nosdn.127.net/catchpic/e/e8/e8af197c3b3ab1786ef430976c9ae8f3.jpg?imageView&thumbnail=550x0";
        CommonOkHttpClient.downloadFile(CommonRequest.createGetResquest(url),new ResponseDataHandle(new ResponseDownloadListener() {

            @Override
            public void onProgress(int progress) {
//           下载进度已封装，可根据需求实现
            }
            @Override
            public void onSuccess(Object responseObj) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(((File)responseObj).getAbsolutePath()));
            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：io错误）
                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(MainActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(MainActivity.this,"文件不存在",Toast.LENGTH_LONG).show();
                }
            }
        },PATH));
    }
}

package com.example.wangyan.oh_my_news_android_client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        button = (Button)findViewById(R.id.BTN_content);
        editText = (EditText)findViewById(R.id.ET_content);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
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
                editText.setText("");
                editText.setText(responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));
    }

}

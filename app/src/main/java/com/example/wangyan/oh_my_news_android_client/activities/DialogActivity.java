package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.classes.message;
import com.example.wangyan.oh_my_news_android_client.classes.msgAdapter;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogActivity extends AppCompatActivity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private msgAdapter adapter;
    static String message0;
    private static String name;

    private List<message> msgList = new ArrayList<message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Intent intent=getIntent();
         name=intent.getStringExtra("name");
        // ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        initMsgs();
        adapter = new msgAdapter(DialogActivity.this, R.layout.listlayout, msgList);
        inputText = (EditText)findViewById(R.id.inputText);
        send = (Button)findViewById(R.id.send);
        msgListView = (ListView)findViewById(R.id.listView);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    message msg = new message("我:"+content, message.TYPE_SEND);
                    postRequest(content);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                }
            }
        });
    }
    private void postRequest(String content){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/privatemsg/postPrivateMsg";

        params.put("userId",123);
        params.put("otherUserId",111);
        params.put("privateMsg",content);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                System.out.println("......");
            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：其他错误）

                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(DialogActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(DialogActivity.this,"数据格式有误",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }

    private void initMsgs() {
        postRequest();
        message msg1 = new message(name+":"+message0, message.TYPE_RECEIVED);
        msgList.add(msg1);
    }
    private void postRequest(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/privatemsg/getExMsg";
        params.put("userId",123);
        params.put("otherUserId",111);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                System.out.println("......");
                try {
                    JSONArray jsonObject = new  JSONArray(responseObj.toString());
                    System.out.println(jsonObject);
                   int len=jsonObject.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject ob =jsonObject.getJSONObject(i);
//                        System.out.println(ob.get("message"));
                        if(ob.getString("username").contains(name))
                        {
                           message0=ob.getString("message");
//                            System.out.println(message0);

                        }
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
                    Toast.makeText(DialogActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(DialogActivity.this,"数据格式有误",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
}
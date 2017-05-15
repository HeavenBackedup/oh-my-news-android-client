package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateMsgActivity extends AppCompatActivity {
    Context context;
    private ListView listView;
    private List<Map<String, Object>> tt;
    private List<String>name;
    private List<String>path;
    private Bitmap bitmap;
    private int userId;
    private  boolean isLoginSuccess;
//    private String[] name=new String[]{"1","1","1","1"};
//    private String[] path=new String[]{"1","1","1","1"};
    private int len;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_msg);
        ExitApplication.getInstance().addActivity(this);
        isLoginSuccess = ExitApplication.getInstance().isLoginSuccess;
        userId = ExitApplication.getInstance().userId;
        context = this;
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        listView = (ListView) findViewById(R.id.listView);
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.item1layout, null);
        postRequest();
    }
    private void postRequest() {
        Map<String, Object> params = new HashMap<String, Object>();
        String url = "/privatemsg/getPrivateMsg";
//        params.put("userId",2);
        params.put("userId",userId);
        params.put("selectedValue",0);
        //System.out.println(params.get("selectedValue"));
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url, params), new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
//                System.out.println("fffffffffff");
                System.out.println(responseObj);
                try {
                    JSONArray jsonObject = new JSONArray(responseObj.toString());
                    len=jsonObject.length();
                    name=new ArrayList<String>();
                    path=new ArrayList<String>();
                    for (int i = 0; i < len; i++) {
                        JSONObject ob =jsonObject.getJSONObject(i);
                        System.out.println(ob);
                         path.add(ob.getString("avatar")) ;
                         name.add(ob.getString("username"));
//                        System.out.println(name);
                    }
                    try {
                        for(int j=0;j<len;j++)
                            downloadFileOther(j);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：其他错误）

                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null) {
                    Toast.makeText(PrivateMsgActivity.this, "网络不稳定", Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null) {
                    Toast.makeText(PrivateMsgActivity.this, "数据格式有误", Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
    private void downloadFileOther(int i) throws FileNotFoundException {
//        String url = "http://cms-bucket.nosdn.127.net/catchpic/e/e8/e8af197c3b3ab1786ef430976c9ae8f3.jpg?imageView&thumbnail=550x0";
        String url=path.get(i);

        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url),new ResponseDataHandle(new ResponseDownloadListener() {
            @Override
            public void onProgress(int progress) {
//           下载进度已封装，可根据需求实现
            }
            @Override
            public void onSuccess(Object responseObj) {

                bitmap = (Bitmap) responseObj;

                tt= new ArrayList<>();
                for(int j=0;j<len;j++) {
                    if (bitmap != null) {
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("picture", bitmap);
                       // System.out.println(bitmap);
                        map1.put("name", name.get(j));
                       // System.out.println(name.get(j));
                        tt.add(map1);
                    }
                }
                System.out.println(tt);
                SimpleAdapter adapter = new SimpleAdapter(context, tt, R.layout.item1layout, new String[]{"picture", "name"}, new int[]{R.id.image1, R.id.text1});
                adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView iv = (ImageView) view;
                            iv.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
                        System.out.println(arg2);
                        Map<String, Object> mm = tt.get(arg2);
                        String name1= (String) mm.get("name");
                        System.out.println(name1);
                        Intent intent = new Intent();
                        intent.setClass(PrivateMsgActivity.this, DialogActivity.class);
                        intent.putExtra("name", name1);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：io错误）
                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(PrivateMsgActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(PrivateMsgActivity.this,"文件不存在",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
}




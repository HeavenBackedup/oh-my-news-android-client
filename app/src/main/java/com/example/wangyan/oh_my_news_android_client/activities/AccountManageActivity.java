package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.MainActivity;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManageActivity extends AppCompatActivity {
    Context context;
    private ListView list;
    private Button button;
//    private Button button0;
    private List<Map<String,Object>> tt;
//    static  int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountmanage);
        ExitApplication.getInstance().addActivity(this);
//        Intent intent0=getIntent();
//        String content0=intent0.getStringExtra("content");
//       TextView text=(TextView)findViewById(R.id.text);
//        text.setText(content0);
        context=this;
        final LayoutInflater layoutInflater=LayoutInflater.from(context);
        button=(Button)findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent0=new Intent();
                intent0.setClass(AccountManageActivity.this,HomepageActivity.class);
                startActivity(intent0);

            }
        });
//        button0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        list = (ListView) findViewById(R.id.lsView);
        postRequest();
    }
    private void postRequest(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/accountManage/getUser";
        params.put("userId","1");

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
//                    System.out.println(jsonObject);
                    tt=new ArrayList<>();
                    Map<String, Object> map1= new HashMap<>();
                    Map<String, Object> map2= new HashMap<>();
                    Map<String, Object> map3= new HashMap<>();
                    String name=jsonObject.getString("username");
//                    System.out.println(name);
                    JSONArray jsonArray=jsonObject.getJSONArray("address");
                    String addr1=jsonArray.getString(1);
                    String addr2=jsonArray.getString(2);
                    String address=addr1+"  "+addr2;
//                    System.out.println(address);
                    String email=jsonObject.getString("email");
//                    System.out.println(email);
                    map1.put("title","用户名");
                    map1.put("content",name);
                    tt.add(map1);
                    map2.put("title","地址");
                    map2.put("content",address);
                    tt.add(map2);
                    map3.put("title","电子邮箱");
                    map3.put("content",email);
                    tt.add(map3);
                    SimpleAdapter adapter=new SimpleAdapter(context,tt,R.layout.itemlayout,new String[]{"title","content"},new int[]{R.id.index,R.id.text});
                    list.setAdapter(adapter);
//                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
////                            System.out.println(arg2);
//                            Map<String,Object> mm =  tt.get(arg2);
//                            position=arg2;
//                            System.out.println(position+"cccccc");
//                            String content=(String )mm.get("content");
//                            System.out.println(content);
//                            Intent intent=new Intent();
//                            intent.setClass(AccountManageActivity.this, UpdateActivity.class);
//                            intent.putExtra("content",content);
//                            startActivity(intent);
//                        }
//                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：其他错误）

                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(AccountManageActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(AccountManageActivity.this,"数据格式有误",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
}

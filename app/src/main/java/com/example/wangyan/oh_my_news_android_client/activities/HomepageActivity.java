package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.HomepageAdapter;
import com.example.wangyan.oh_my_news_android_client.connection.HomepageConnection;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.service.HomepageService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Intent intent;
    private HomepageUserInfo homepageUserInfo;
    private Handler handler;
    private HomepageConnection connection;
    private HomepageService homepageService;
    private boolean isTransported=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
//        Intent service=new Intent(this, HomepageService.class);

        while (homepageUserInfo==null){
            getHomepageInfo();
            recyclerView=(RecyclerView)findViewById(R.id.homepage_recyclerView_view);
            final List<MultiItemOfHomepage> data= DataServerForHomepage.getMultiItemData();
            final HomepageAdapter homepageAdapter=new HomepageAdapter(this,data,homepageUserInfo);
            final GridLayoutManager manager=new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(homepageAdapter);
            homepageAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                    return data.get(position).getSpanSize();
                }
            });
            homepageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Toast.makeText(HomepageActivity.this,"item "+position,Toast.LENGTH_SHORT).show();
                    switch (position){
                        case 3:
                            intent=new Intent(HomepageActivity.this,MyCollectionActivity.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent=new Intent(HomepageActivity.this,MyArticleActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent=new Intent(HomepageActivity.this,MyFansListActivity.class);
                            startActivity(intent);

                    }
                }
            });

        }





    }


    public static HomepageUserInfo getUserInfo(){
//        final Handler handler=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Log.i("masd", String.valueOf(msg.getData()));
//            }
//        };
        final HomepageUserInfo homepageUserInfo=new HomepageUserInfo();
        Map<String,Object> params = new HashMap<String,Object>();
        String url="/homePage/common";
        params.put("userId",1);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj)  {
//
//                Bundle bundle=new Bundle();
//                Message msg=new Message();
//                bundle.putString("response", (String) responseObj);
//                msg.setData(bundle);
//                handler.sendMessage(msg);
                Log.i("responseObj",responseObj.toString()+111);
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

                    }
                } catch (Exception e) {
                    Log.i("exp",e.toString()+111);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.i("error",reasonObj.toString()+111);

            }
        }));

        return homepageUserInfo;
    }

    public void getHomepageInfo(){
        intent=new Intent(this,HomepageService.class);
        intent.putExtra("userId",1);
//        startService(intent);
        connection=new HomepageConnection();
        bindService(intent,connection,BIND_AUTO_CREATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isTransported){
                    homepageService=connection.getHomepageService();
                    if (homepageService!=null){
                        Log.i("service","fanfan");
                        homepageService.setCallback(new HomepageService.Callback(){

                            @Override
                            public void onDataChange(Object data) {
                                homepageUserInfo= (HomepageUserInfo) data;
                                if (homepageUserInfo!=null){
                                    isTransported=false;
                                }

                            }
                        });
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}

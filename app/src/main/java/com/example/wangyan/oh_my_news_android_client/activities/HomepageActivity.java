package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.HomepageAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;
import com.example.wangyan.oh_my_news_android_client.util.RequestBodyForm;
import com.example.wangyan.oh_my_news_android_client.util.SendOkHttpRequestPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Intent intent;
    private HomepageUserInfo homepageUserInfo;
    private Handler handler;
    private int userId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
//        Intent intent=getIntent();
//        userId=intent.getIntExtra("userId",-1);
        Thread thread=new GetHomepageInfo(userId);
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                homepageUserInfo= (HomepageUserInfo) msg.getData().get("homepageUserInfo");
                recyclerView=(RecyclerView)findViewById(R.id.homepage_recyclerView_view);
                final List<MultiItemOfHomepage> data= DataServerForHomepage.getMultiItemData();
                final HomepageAdapter homepageAdapter=new HomepageAdapter(HomepageActivity.this,data,homepageUserInfo);
                final GridLayoutManager manager=new GridLayoutManager(HomepageActivity.this,2);
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
                            case 4:
                                intent=new Intent(HomepageActivity.this,MyCollectionActivity.class);
                                intent.putExtra("userId",userId);
                                startActivity(intent);
                                break;
                            case 5:
                                intent=new Intent(HomepageActivity.this,MyArticleActivity.class);
                                intent.putExtra("userId",homepageUserInfo.getUserId());
                                intent.putExtra("avatarPic",homepageUserInfo.getAvatar());
                                intent.putExtra("nickName",homepageUserInfo.getNickname());
                                startActivity(intent);
                                break;
                            case 2:
                                intent=new Intent(HomepageActivity.this,MyFansListActivity.class);
                                intent.putExtra("userId",homepageUserInfo.getUserId());
                                startActivity(intent);
                                break;
                            case 1:
                                intent=new Intent(HomepageActivity.this,ConcernsActivity.class);
                                intent.putExtra("userId",homepageUserInfo.getUserId());
                                startActivity(intent);
                                break;

                        }
                    }
                });
                Log.i("homepageuserinfo",homepageUserInfo.toString());
            }
        };


    }

    public  class GetHomepageInfo extends Thread{
        private int userId;

        public GetHomepageInfo(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            super.run();
            Log.i("Thread123",Thread.currentThread().getName());
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/homePage/common";
            params.put("userId",userId);
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            String updateUrl=requestBodyForm.getUrl();
            final RequestBody requestBody=requestBodyForm.getRequestBody();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    HomepageUserInfo homepageUserInfo=new HomepageUserInfo();
                    try {
                        String line=response.body().string();
                        JSONObject jsonObject=new JSONObject(line).getJSONObject("data");
                        homepageUserInfo.setAvatar((String) jsonObject.get("avatarPath"));
                        homepageUserInfo.setUserId((Integer) jsonObject.get("usersId"));
                        homepageUserInfo.setNickname((String) jsonObject.get("nickName"));
                        homepageUserInfo.setSignature((String) jsonObject.get("signature"));
                        homepageUserInfo.setConcerns((Integer) jsonObject.get("fans"));
                        homepageUserInfo.setFans((Integer) jsonObject.get("followers"));
                        homepageUserInfo.setAnnouncement((String) jsonObject.get("announcement"));
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("homepageUserInfo",homepageUserInfo);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },requestBody);

        }
    }

}

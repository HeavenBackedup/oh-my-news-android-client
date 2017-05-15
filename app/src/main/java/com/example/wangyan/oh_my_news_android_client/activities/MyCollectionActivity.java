package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.MyCollectionAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.CollectionInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;
import com.example.wangyan.oh_my_news_android_client.util.RequestBodyForm;
import com.example.wangyan.oh_my_news_android_client.util.SendOkHttpRequestPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyCollectionActivity extends BaseActivity {
    private RecyclerView recyclerView;

    private int length;
    private int userIdOfLogin;
    private int userIdOfShow;
    private CollectionInfo collectionInfo;
    private Intent intent;
    private boolean isLoginSuccess;
    private Handler handler;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ExitApplication.getInstance().addActivity(this);
        setTitle("我的收藏");
        setBackBtn();
        intent=getIntent();
        userIdOfLogin=intent.getIntExtra("userId",-1);
        isLoginSuccess=intent.getBooleanExtra("isLoginSuccess",false);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.collection_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread threadCollection=new GetMyCollection(userIdOfLogin);
                threadCollection.start();
            }
        });
        Thread thread=new GetMyCollection(userIdOfLogin);
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                final List<CollectionInfo> list=new ArrayList<>();
                String result=msg.getData().getString("result");
                try {
                    JSONArray jsonArray=new JSONArray(result);
                    length=jsonArray.length();
                    for (int i=0;i<length;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        CollectionInfo collectionInfo=new CollectionInfo();
                        collectionInfo.setArticleId(jsonObject.getInt("articleId"));
                        collectionInfo.setTopic(jsonObject.getString("topic"));
                        collectionInfo.setNickname(jsonObject.getString("nickname"));
                        collectionInfo.setArticleContent(jsonObject.getString("articleContent"));
                        collectionInfo.setUserId(jsonObject.getInt("authorId"));
                        collectionInfo.setAvatar(jsonObject.getString("avatar"));
                        collectionInfo.setCollectedNum(jsonObject.getInt("collectedNum"));
                        list.add(collectionInfo);
                    }
                    if (list.size()==0){
                        Toast.makeText(MyCollectionActivity.this,"您还没有收藏的文章哦",Toast.LENGTH_SHORT).show();
                    }
                        recyclerView=(RecyclerView)findViewById(R.id.collection_recycler_view);
                        final List<MultiItemOfCollection> data= DataServerForHomepage.getMultiItemCollectionData(length);
                        MyCollectionAdapter myCollectionAdapter=new MyCollectionAdapter(MyCollectionActivity.this,data,list);
                        final LinearLayoutManager manager=new LinearLayoutManager(MyCollectionActivity.this);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(myCollectionAdapter);
                        myCollectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                int index=position/4;
                               CollectionInfo collectionInfo=list.get(index);
                                Toast.makeText(MyCollectionActivity.this,"item"+position+"userId"+collectionInfo.getUserId(),Toast.LENGTH_SHORT).show();
                                if (position%4==0){
                                    intent=new Intent(MyCollectionActivity.this,OthersHomepageActivity.class);
                                    intent.putExtra("userIdOfShow",collectionInfo.getUserId());
                                    intent.putExtra("userIdOfLogin",userIdOfLogin);
                                    intent.putExtra("nickname",collectionInfo.getNickname());
                                    intent.putExtra("isLoginSuccess",isLoginSuccess);
                                    startActivity(intent);

                                }else {
                                    intent=new Intent(MyCollectionActivity.this,DetailActivity.class);
                                    intent.putExtra("isLoginSuccess",isLoginSuccess);
                                    intent.putExtra("articleId",collectionInfo.getArticleId());
                                    intent.putExtra("userId",userIdOfLogin);
                                    startActivity(intent);
                                }

                            }
                        });
                        myCollectionAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };



    }

    public class GetMyCollection extends Thread{
        private int userId;

        public GetMyCollection(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params=new HashMap<>();
            String url="/like_collect/getContents";
            params.put("userId",userId);
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            String updateUrl=requestBodyForm.getUrl();
            RequestBody requestBody=requestBodyForm.getRequestBody();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                String line=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(line).getJSONObject("data");
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        String result=jsonArray.toString();
                        Bundle bundle=new Bundle();
                        Message message=new Message();
                        bundle.putString("result",result);
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

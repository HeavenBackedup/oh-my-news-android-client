package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.wangyan.oh_my_news_android_client.util.RequestBodyForm;
import com.example.wangyan.oh_my_news_android_client.util.SendOkHttpRequestPost;

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
    private List<CollectionInfo> list=new ArrayList<>();
    private int length;
    private int userId;
    private CollectionInfo collectionInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        setTitle("我的收藏");
        setBackBtn();
        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",-1);
        list=DataServerForHomepage.getCollectionInfo();
        length=list.size();
        recyclerView=(RecyclerView)findViewById(R.id.collection_recycler_view);
        final List<MultiItemOfCollection> data= DataServerForHomepage.getMultiItemCollectionData(length);
        MyCollectionAdapter myCollectionAdapter=new MyCollectionAdapter(this,data,list);
        final LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myCollectionAdapter);

        myCollectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int index=position/4;
                collectionInfo=list.get(index);
                Toast.makeText(MyCollectionActivity.this,"item"+position+"userId"+collectionInfo.getUserId(),Toast.LENGTH_SHORT).show();
                switch (position%4){
                    case 0:
                        Toast.makeText(MyCollectionActivity.this,"type name",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MyCollectionActivity.this,"type context",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MyCollectionActivity.this,"type context",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MyCollectionActivity.this,"type collect",Toast.LENGTH_SHORT).show();

                        break;

                }


            }
        });
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
            String url="/Like_collect/getContents";
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


                }
            },requestBody);

        }
    }

}

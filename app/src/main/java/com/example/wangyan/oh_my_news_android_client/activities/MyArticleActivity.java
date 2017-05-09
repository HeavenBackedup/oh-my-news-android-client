package com.example.wangyan.oh_my_news_android_client.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.MyArticleAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;

import java.util.List;

public class MyArticleActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private String nickname;
    private int avart_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_article);
        setTitle("我的文章");
        setBackBtn();
        recyclerView=(RecyclerView)findViewById(R.id.article_recycler_view);
        final List<MultiItemOfCollection> data= DataServerForHomepage.getMuiliItemArticleData();
        MyArticleAdapter myArticleAdapter=new MyArticleAdapter(this,data);
        final GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myArticleAdapter);
        myArticleAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });

    }
}

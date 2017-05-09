package com.example.wangyan.oh_my_news_android_client.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.MyCollectionAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;

import java.util.List;

public class MyCollectionActivity extends BaseActivity {
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        setTitle("我的收藏");
        recyclerView=(RecyclerView)findViewById(R.id.collection_recycler_view);
        final List<MultiItemOfCollection> data= DataServerForHomepage.getMultiItemCollectionData();
        MyCollectionAdapter myCollectionAdapter=new MyCollectionAdapter(this,data);
        final GridLayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myCollectionAdapter);
        myCollectionAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        myCollectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MyCollectionActivity.this,"item"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

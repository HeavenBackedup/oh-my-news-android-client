package com.example.wangyan.oh_my_news_android_client.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.FansAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;

import java.util.List;

public class MyFansListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans_list);
        setTitle("我的粉丝");
        recyclerView=(RecyclerView)findViewById(R.id.fans_list_recycler_view);
        final List<MultiItemOfFans> data= DataServerForHomepage.getMuliItemFansData();
        FansAdapter fansAdapter=new FansAdapter(this,data);
        GridLayoutManager manager=new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(fansAdapter);
        fansAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });

    }
}

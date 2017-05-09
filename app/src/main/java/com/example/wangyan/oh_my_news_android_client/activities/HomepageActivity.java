package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.HomepageAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;

import java.util.List;

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Intent intent;
//    public HomepageList[] homepageList={new HomepageList("我的收藏",R.mipmap.ios7_heart_outline),new HomepageList("我的文章",R.mipmap.ios_document),new HomepageList("账号设置",R.mipmap.ios7_gear_outline),new HomepageList("我的钱包",R.mipmap.ios_card)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
//        setTitle("我的账号");
//
//        setBackBtnPic();
//                setBackBtn();
        recyclerView=(RecyclerView)findViewById(R.id.homepage_recyclerView_view);
        final List<MultiItemOfHomepage> data= DataServerForHomepage.getMultiItemData();
        final HomepageAdapter homepageAdapter=new HomepageAdapter(this,data);
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

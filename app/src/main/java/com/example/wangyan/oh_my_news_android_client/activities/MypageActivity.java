package com.example.wangyan.oh_my_news_android_client.activities;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.classes.contentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MypageActivity extends ActivityGroup  {
    private View view1,view2,view3,view4;
    private LinearLayout Home;
    private LinearLayout Search;
    private LinearLayout Pmsg;
    private LinearLayout Personal;
    // 底部菜单4个菜单标题
    private TextView txtHome;
    private TextView txtSearch;
    private TextView txtPmsg;
    private TextView txtPersonal;
    private int userId;
    private  boolean isLoginSuccss;

    // 中间内容区域
    private ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private contentAdapter adapter;

    private List<View> views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Intent intent=getIntent();
        intent.getStringExtra("userId");
        intent.getStringExtra("isLoginSuccess");
        userId=1;
        isLoginSuccss=false;
        Home=(LinearLayout)findViewById(R.id.Home);
        Search=(LinearLayout)findViewById(R.id.Search);
        Pmsg=(LinearLayout)findViewById(R.id.Pmsg);
        Personal=(LinearLayout)findViewById(R.id.Personal);
        txtHome= (TextView) findViewById(R.id.txtHome);
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txtPmsg= (TextView) findViewById(R.id.txtPmsg);
        txtPersonal= (TextView) findViewById(R.id.txtPersonal);
        initView();
        initEvent();


    }
    private void initView(){
        viewPager=(ViewPager)findViewById(R.id.vp_content);
        view1=getLocalActivityManager().startActivity("home",new Intent(MypageActivity.this,MainpageActivity.class)).getDecorView();
        view2=getLocalActivityManager().startActivity("search",new Intent(MypageActivity.this,SearchActivity.class)).getDecorView();
        view3=getLocalActivityManager().startActivity("primsg",new Intent(MypageActivity.this,PrivateMsgActivity.class)).getDecorView();
        view4=getLocalActivityManager().startActivity("personal",new Intent(MypageActivity.this,HomepageActivity.class)).getDecorView();
        views=new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        this.adapter = new contentAdapter(views);
        viewPager.setAdapter(adapter);
       txtHome.setTextColor(0xff1B940A);
    }
    private void initEvent(){

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBotton();
                txtHome.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(0);

            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBotton();
                txtSearch.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(1);
            }
        });

        Pmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId!=0){
                    restartBotton();
                    txtPmsg.setTextColor(0xff1B940A);
                    viewPager.setCurrentItem(2);
                }
                else{
                    Intent intent1=new Intent();
                    intent1.setClass(MypageActivity.this,LoginActivity.class);
                    intent1.putExtra("viewPage",2);
                    startActivity(intent1);
                }


            }
        });

        Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId!=0){
                    restartBotton();
                    txtPersonal.setTextColor(0xff1B940A);
                    viewPager.setCurrentItem(3);
                }
                else{
                    Intent intent1=new Intent();
                    intent1.setClass(MypageActivity.this,LoginActivity.class);
                    intent1.putExtra("viewPage",3);
                    startActivity(intent1);
                }

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
               restartBotton();
                //当前view被选择的时候,改变底部菜单文字颜色
                switch (arg0) {
                    case 0:
               txtHome.setTextColor(0xff1B940A);
                        break;
                    case 1:
               txtSearch.setTextColor(0xff1B940A);
                        break;
                    case 2:
                        if(userId!=0){
                            txtPmsg.setTextColor(0xff1B940A);
                        }
                        else{
                            Intent intent1=new Intent();
                            intent1.setClass(MypageActivity.this,LoginActivity.class);
                            intent1.putExtra("viewPage",3);
                            startActivity(intent1);
                        }

                        break;
                    case 3:
                        if(userId!=0&&!isLoginSuccss){
                            txtPersonal.setTextColor(0xff1B940A);
                        }
                        else{
                            Intent intent1=new Intent();
                            intent1.setClass(MypageActivity.this,LoginActivity.class);
                            intent1.putExtra("viewPage",3);
                            startActivity(intent1);
                        }
                        break;

                    default:
                        break;
                }

            }
        });

    }
    private void restartBotton() {
       txtHome.setTextColor(0xff000000);
        txtSearch.setTextColor(0xff000000);
       txtPmsg.setTextColor(0xff000000);
       txtPersonal.setTextColor(0xff000000);
    }
}
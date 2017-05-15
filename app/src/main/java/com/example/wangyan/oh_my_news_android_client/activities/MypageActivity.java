package com.example.wangyan.oh_my_news_android_client.activities;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.classes.contentAdapter;
import com.example.wangyan.oh_my_news_android_client.util.AutoLogin;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MypageActivity extends ActivityGroup  {
    private View view1,view2,view3,view4,view5;
    private LinearLayout Home;
    private LinearLayout Search;
    private LinearLayout Pmsg;
    private LinearLayout Personal;
    private TextView txtHome;
    private TextView txtSearch;
    private TextView txtPmsg;
    private TextView txtPersonal;
    private String type="";
    private int userId;
    private  boolean isLoginSuccss;
    private ViewPager viewPager;
    private ViewPager viewPager1;
    private contentAdapter adapter;
    private contentAdapter adapter1;

    private List<View> views;
    private List<View> views1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ExitApplication.getInstance().addActivity(this);
        Intent intent=getIntent();
        System.out.println(intent);
//        userId=intent.getIntExtra("userId",-1);
//        isLoginSuccss=intent.getBooleanExtra("isLoginSuccess",false);
//        type=intent.getStringExtra("type");
//        AutoLogin l=new AutoLogin();
//        Map<String,Object> map=l.login();
        userId=1;
        isLoginSuccss=false;
//        userId=Integer.parseInt(map.get("userId").toString());
//        isLoginSuccss=Boolean.parseBoolean(map.get("isLoginSuccess").toString());
        Home=(LinearLayout)findViewById(R.id.Home);
        Search=(LinearLayout)findViewById(R.id.Search);
        Pmsg=(LinearLayout)findViewById(R.id.Pmsg);
        Personal=(LinearLayout)findViewById(R.id.Personal);
        txtHome= (TextView) findViewById(R.id.txtHome);
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txtPmsg= (TextView) findViewById(R.id.txtPmsg);
        txtPersonal= (TextView) findViewById(R.id.txtPersonal);
        if(isLoginSuccss){
            initView();
            onClickEvent();
           pageChangeEvent();
        }
        else {
            initView();
            onClickEvent();
            initView1();
           pageChangeEvent1();

        }
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
        if(type.equals("privateMsg")){
            viewPager.setCurrentItem(2);
            txtPmsg.setTextColor(0xffFF0000);
        }
        else
            txtHome.setTextColor(0xffFF0000);


        if(type.equals("homePage")){
            viewPager.setCurrentItem(3);
            txtPersonal.setTextColor(0xffFF0000);
        }
        else
            txtHome.setTextColor(0xffFF0000);
    }
    private void initView1(){
        viewPager1=(ViewPager)findViewById(R.id.vp_content);
        view1=getLocalActivityManager().startActivity("home",new Intent(MypageActivity.this,MainpageActivity.class)).getDecorView();
        view2=getLocalActivityManager().startActivity("search",new Intent(MypageActivity.this,SearchActivity.class)).getDecorView();
        view5=getLocalActivityManager().startActivity("login",new Intent(MypageActivity.this,LoginActivity.class)).getDecorView();
        views1=new ArrayList<View>();
        views1.add(view1);
        views1.add(view2);
        views1.add(view5);
        this.adapter1 = new contentAdapter(views1);
        viewPager1.setAdapter(adapter1);
        if(type.equals("privateMsg")){
            viewPager.setCurrentItem(2);
            txtPmsg.setTextColor(0xffFF0000);
        }
        else
            txtHome.setTextColor(0xffFF0000);


        if(type.equals("homePage")){
            viewPager.setCurrentItem(3);
            txtPersonal.setTextColor(0xffFF0000);
        }
        else
            txtHome.setTextColor(0xffFF0000);
    }
    private void onClickEvent() {

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBotton();
                txtHome.setTextColor(0xffFF0000);
                viewPager.setCurrentItem(0);

            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBotton();
                txtSearch.setTextColor(0xffFF0000);
                viewPager.setCurrentItem(1);
            }
        });

        Pmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginSuccss) {
                    restartBotton();
                    txtPmsg.setTextColor(0xffFF0000);
                    viewPager.setCurrentItem(2);
                } else {
                    Intent intent1 = new Intent();
                    intent1.setClass(MypageActivity.this, LoginActivity.class);
                    intent1.putExtra("type", "privateMsg");
                    startActivity(intent1);
                }


            }
        });

        Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginSuccss) {
                    restartBotton();
                    txtPersonal.setTextColor(0xffFF0000);
                    viewPager.setCurrentItem(3);
                } else {
                    Intent intent1 = new Intent();
                    intent1.setClass(MypageActivity.this, LoginActivity.class);
                    intent1.putExtra("type", "homePage");
                    startActivity(intent1);
                }

            }
        });
    }
    private void pageChangeEvent(){
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
                switch (arg0) {
                    case 0:
                        txtHome.setTextColor(0xffFF0000);
                        break;
                    case 1:
                        txtSearch.setTextColor(0xffFF0000);
                        break;
                    case 2:
//                        if(isLoginSuccss){
//                            txtPmsg.setTextColor(0xffFF0000);
//                        }
//                        else{
//                            Intent intent1=new Intent();
//                            intent1.setClass(MypageActivity.this,LoginActivity.class);
//                            intent1.putExtra("type","privateMsg");
//                            startActivity(intent1);
//                        }
                        txtPmsg.setTextColor(0xffFF0000);

                        break;
                    case 3:
                        txtPersonal.setTextColor(0xffFF0000);
//                        if(isLoginSuccss){
//                            txtPersonal.setTextColor(0xffFF0000);
//                        }
//                        else{
//                            Intent intent1=new Intent();
//                            intent1.setClass(MypageActivity.this,LoginActivity.class);
//                            intent1.putExtra("type","homePage");
//                            startActivity(intent1);
//                        }
//                        break;

                    default:
                        break;
                }

            }
        });

    }

    private void pageChangeEvent1(){
        viewPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageSelected(int arg0) {
                restartBotton();
                switch (arg0) {
                    case 0:
                        txtHome.setTextColor(0xffFF0000);
                        break;
                    case 1:
                        txtSearch.setTextColor(0xffFF0000);
                        break;
                    case 2:
                        txtHome.setTextColor(0x00FFFFFF);
                        txtSearch.setTextColor(0x00FFFFFF);
                        txtPmsg.setTextColor(0x00FFFFFF);
                        txtPersonal.setTextColor(0x00FFFFFF);
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
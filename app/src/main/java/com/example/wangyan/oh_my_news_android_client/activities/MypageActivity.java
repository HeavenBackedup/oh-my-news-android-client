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
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import java.util.ArrayList;
import java.util.List;

public class MypageActivity extends ActivityGroup  {
    private View view1,view2,view3,view4,view5,view6;
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
    private  boolean isLoginSuccess;
    private ViewPager viewPager;
    private ViewPager viewPager1;
    private contentAdapter adapter;
    private contentAdapter adapter1;
    private String pageType="home";

    private List<View> views;
    private List<View> views1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ExitApplication.getInstance().addActivity(this);
        Intent intent=getIntent();
//        System.out.println(intent);
//        userId=intent.getIntExtra("userId",-1);
//        isLoginSuccss=intent.getBooleanExtra("isLoginSuccess",false);
        type=intent.getStringExtra("type");
//        AutoLogin l=new AutoLogin();
//        Map<String,Object> map=l.login();
//        userId=1;
//        isLoginSuccess=true;
        isLoginSuccess = ExitApplication.getInstance().isLoginSuccess;
        userId = ExitApplication.getInstance().userId;
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
        if(isLoginSuccess){
            initView();
            onClickEvent();
           pageChangeEvent();
        }
        else {
//            initView();
            initView1();
            onClickEvent1();

           pageChangeEvent1();

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restartBotton();
        isLoginSuccess = ExitApplication.getInstance().isLoginSuccess;
        userId = ExitApplication.getInstance().userId;
        if(isLoginSuccess){
            initView();
            onClickEvent();
            pageChangeEvent();
            switch (pageType){
                case "home":
                    restartBotton();
                    txtHome.setTextColor(0xffFF0000);
                    viewPager.setCurrentItem(0);
                    break;
                case "search":
                    restartBotton();
                    txtSearch.setTextColor(0xffFF0000);
                    viewPager.setCurrentItem(1);
                    break;
                case "pmsg":
                    restartBotton();
                    txtPmsg.setTextColor(0xffFF0000);
                    System.out.println("pmsg..............");
                    viewPager.setCurrentItem(2);
                    break;
                case "personal":
                    restartBotton();
                    txtPersonal.setTextColor(0xffFF0000);
                    System.out.println("personal..............");
                    viewPager.setCurrentItem(3);
                    break;
            }
        }
        else {
//            initView();
            initView1();
            onClickEvent1();

            pageChangeEvent1();
            switch (pageType){
                case "home":
                    restartBotton();
                    txtHome.setTextColor(0xffFF0000);
                    viewPager1.setCurrentItem(0);
                    break;
                case "search":
                    restartBotton();
                    txtSearch.setTextColor(0xffFF0000);
                    viewPager1.setCurrentItem(1);
                    break;
                case "pmsg":
                    Intent intent1 = new Intent();
                    intent1.setClass(MypageActivity.this, LoginActivity.class);
                    intent1.putExtra("type", "privateMsg");
                    startActivity(intent1);
                    break;
                case "personal":
                    Intent intent2 = new Intent();
                    intent2.setClass(MypageActivity.this, LoginActivity.class);
                    intent2.putExtra("type", "homePage");
                    startActivity(intent2);
                    break;
            }

        }
    }

    private void initView(){
        viewPager=(ViewPager)findViewById(R.id.vp_content);
        System.out.println("jjjjjjjj");
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
        else if(type.equals("homePage")){
                viewPager.setCurrentItem(3);
                txtPersonal.setTextColor(0xffFF0000);
        }else
            txtHome.setTextColor(0xffFF0000);


//        if(type.equals("homePage")){
//            viewPager.setCurrentItem(3);
//            txtPersonal.setTextColor(0xffFF0000);
//        }
//        else
//            txtHome.setTextColor(0xffFF0000);
    }
    private void initView1(){
        viewPager1=(ViewPager)findViewById(R.id.vp_content);
        view1=getLocalActivityManager().startActivity("home1",new Intent(MypageActivity.this,MainpageActivity.class)).getDecorView();
        view2=getLocalActivityManager().startActivity("search1",new Intent(MypageActivity.this,SearchActivity.class)).getDecorView();
        Intent intent=new Intent();
        intent.setClass(MypageActivity.this,LoginActivity.class);
        intent.putExtra("type","privateMsg");
        view5=getLocalActivityManager().startActivity("login",intent).getDecorView();
//        view6=getLocalActivityManager().startActivity("login",new Intent(MypageActivity.this,LoginActivity.class)).getDecorView();
        views1=new ArrayList<View>();
        views1.add(view1);
        views1.add(view2);
        views1.add(view5);
//        views1.add(view6);
        this.adapter1 = new contentAdapter(views1);
        viewPager1.setAdapter(adapter1);
        txtHome.setTextColor(0xffFF0000);
        if(type.equals("privateMsg")){
            viewPager.setCurrentItem(2);
            txtPmsg.setTextColor(0xffFF0000);
        }
        else if(type.equals("homePage")){
            viewPager.setCurrentItem(3);
            txtPersonal.setTextColor(0xffFF0000);
        }else
            txtHome.setTextColor(0xffFF0000);
    }
    private void onClickEvent() {

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageType="home";
                restartBotton();
                txtHome.setTextColor(0xffFF0000);
                viewPager.setCurrentItem(0);

            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageType="search";
                restartBotton();
                txtSearch.setTextColor(0xffFF0000);
                viewPager.setCurrentItem(1);
            }
        });

        Pmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginSuccess) {
                    pageType="pmsg";
                    System.out.println("pmsgpmsg......");
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
                if (isLoginSuccess) {
                    pageType="personal";
                    System.out.println("peraonalperaonal......");
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
    private void onClickEvent1() {

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBotton();
                pageType="home";
                txtHome.setTextColor(0xffFF0000);
                viewPager1.setCurrentItem(0);

            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBotton();
                pageType="search";
                txtSearch.setTextColor(0xffFF0000);
                viewPager1.setCurrentItem(1);
            }
        });

        Pmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginSuccess) {
                    pageType="pmsg";
                    restartBotton();
                    txtPmsg.setTextColor(0xffFF0000);
                    viewPager1.setCurrentItem(2);
                } else {
                    pageType="pmsg";
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
                if (isLoginSuccess) {
                    restartBotton();
                    pageType="personal";
                    txtPersonal.setTextColor(0xffFF0000);
                    viewPager1.setCurrentItem(2);
                } else {
                    pageType="personal";
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
                        pageType="home";
                        txtHome.setTextColor(0xffFF0000);
                        break;
                    case 1:
                        pageType="search";
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
//                        pageType="pmsg";
                        txtPmsg.setTextColor(0xffFF0000);

                        break;
                    case 3:
//                        pageType="personal";
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
                        pageType="home";
                        txtHome.setTextColor(0xffFF0000);
                        break;
                    case 1:
                        pageType="search";
                        txtSearch.setTextColor(0xffFF0000);
                        break;
                    case 2:
                        pageType="pmsg";
                        txtHome.setTextColor(0x00FFFFFF);
                        txtSearch.setTextColor(0x00FFFFFF);
                        txtPmsg.setTextColor(0x00FFFFFF);
                        txtPersonal.setTextColor(0x00FFFFFF);
//                    case 3:
//                        txtHome.setTextColor(0x00FFFFFF);
//                        txtSearch.setTextColor(0x00FFFFFF);
//                        txtPmsg.setTextColor(0x00FFFFFF);
//                        txtPersonal.setTextColor(0x00FFFFFF);
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
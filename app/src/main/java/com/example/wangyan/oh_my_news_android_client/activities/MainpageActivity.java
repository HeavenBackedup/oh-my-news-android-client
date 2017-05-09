package com.example.wangyan.oh_my_news_android_client.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.MainActivity;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.services.MainpageService;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.MainpageConnection;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.Topbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_AUTHOR;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_ID;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_IMGS;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_TITLE;

public class MainpageActivity extends AppCompatActivity{

    private Topbar topbar_main;
    private ListView lv_news;
    private SwipeRefreshLayout news_refresh;
    private Handler handler;

    private MainpageConnection conn;
    private MainpageService mainpageService;
    private Intent intent = new Intent();
    private boolean isExit = false;
    private List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        initView();
    }
    private void initView(){
        topbar_main = (Topbar)findViewById(R.id.topbar_main);
        lv_news = (ListView)findViewById(R.id.lv_news);
        news_refresh = (SwipeRefreshLayout)findViewById(R.id.news_refresh);
        topbar_main.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainpageActivity.this);
                exit(builder);
            }
            @Override
            public void rightClick() {
                Toast.makeText(MainpageActivity.this,"分类",Toast.LENGTH_LONG).show();

            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                list = (List<Map<String,Object>>)msg.obj;
                Log.i("yan","............."+list.toString());
                SimpleAdapter simpleAdapter = new SimpleAdapter(MainpageActivity.this,
                        list, R.layout.layout_news_show, new String[] {NEWS_IMGS,NEWS_TITLE,NEWS_AUTHOR},
                        new int[] { R.id.iv_img,R.id.tv_title,R.id.tv_author});
                simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

                    @Override
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView iv = (ImageView) view;
                            iv.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });

                lv_news.setAdapter(simpleAdapter);

            }
        };
        conn= new MainpageConnection();
        intent.setClass(MainpageActivity.this, MainpageService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isExit) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mainpageService = conn.getMainpageService();
                    if (mainpageService != null) {
                        isExit = true;
                        mainpageService.setCallback(new MainpageService.Callback() {
                            @Override
                            public void onDataChange(Object data) {
                                Message msg = new Message();
                                msg.obj = data;
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }
            }
        }).start();
    }
    private void exit(AlertDialog.Builder builder){

        builder.setMessage("确定要退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               MainpageActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //创建并显示弹出的对话框
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        isExit=true;
    }
}

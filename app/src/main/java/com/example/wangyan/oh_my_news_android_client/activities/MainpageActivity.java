package com.example.wangyan.oh_my_news_android_client.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.JsonToObject;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.RefreshAdapter;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.Topbar;
import com.example.wangyan.oh_my_news_android_client.view.RefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_AUTHOR;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_IMGS;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_TITLE;

public class MainpageActivity extends AppCompatActivity{
    private RefreshAdapter adapter;
    private RefreshView lv_refresh_news;

    private static int index = 1;
    private int addValue = 1;
    private Button mainPageButton;
    private Button cultureButton;
    private Button fashionButton;
    private Button entertainmentButton;
    private Button houseButton;
    private Button techologyButton;
    private Button carButton;
    private Button gameButton;
    private Button sportButton;
    private Button bookButton;
    private Topbar topbar_main;
    private Handler handler = new Handler();
    private Intent intent = new Intent();


    private List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
    private int userId;
    private boolean isLoginSuccess;
    private int articleId;
    private int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        ExitApplication.getInstance().addActivity(this);
//        intent = getIntent();
//        isLoginSuccess = intent.getBooleanExtra("isLoginSuccess",false);
//        userId = intent.getIntExtra("userId",-1);
        isLoginSuccess = ExitApplication.getInstance().isLoginSuccess;
        userId = ExitApplication.getInstance().userId;
        Log.i("yanyue", "onCreate: yyyyy"+userId+"...."+isLoginSuccess);
        new Thread(new Runnable() {
            @Override
            public void run() {
                responseNewsData(addValue);
            }
        }).start();

        initView();
    }


    private void initView(){
        topbar_main = (Topbar)findViewById(R.id.topbar_main);
        lv_refresh_news = (RefreshView)findViewById(R.id.lv_refresh_news);
        mainPageButton = (Button)findViewById(R.id.mainPageButton);
        cultureButton = (Button)findViewById(R.id.cultureButton);
        fashionButton = (Button)findViewById(R.id.fashionButton);
        entertainmentButton = (Button)findViewById(R.id.entertainmentButton);
        houseButton = (Button)findViewById(R.id.houseButton);
        techologyButton = (Button)findViewById(R.id.techologyButton);
        carButton = (Button)findViewById(R.id.carButton);
        gameButton = (Button)findViewById(R.id.gameButton);
        sportButton = (Button)findViewById(R.id.sportButton);
        bookButton = (Button)findViewById(R.id.bookButton);

        topbar_main.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainpageActivity.this);
                exit(builder);
            }
            @Override
            public void rightClick() {
                intent.putExtra("type","mainPage");
                intent.setClass(MainpageActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
              sortOnclick(index,mainPageButton);

            }
        });
        cultureButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              index = 2;
              sortOnclick(index, cultureButton);
          }
      });
        fashionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 3;
                sortOnclick(index,fashionButton);
            }
        });
        entertainmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 4;
                sortOnclick(index,entertainmentButton);
            }
        });
        houseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 5;
                sortOnclick(index,houseButton);
            }
        });
        techologyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 6;
                sortOnclick(index,techologyButton);
            }
        });
        carButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 7;
                sortOnclick(index,carButton);
            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 8;
                sortOnclick(index,gameButton);
            }
        });
        sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 9;
                sortOnclick(index,sportButton);
            }
        });
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 10;
                sortOnclick(index,bookButton);
            }
        });

         lv_refresh_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Map<String,Object> map = list.get(position);
              articleId = (int) map.get("articleId");
              intent.putExtra("userId",ExitApplication.getInstance().userId);
              intent.putExtra("isLoginSuccess",ExitApplication.getInstance().isLoginSuccess);
              intent.putExtra("articleId",articleId);
              Log.i("yanyue", "onCreate: "+userId+"...."+isLoginSuccess);
              intent.setClass(MainpageActivity.this,DetailActivity.class);
              startActivity(intent);
             }
          });


        lv_refresh_news.setRefreshCallBack(new RefreshView.RefreshCallBack() {
            @Override
            public void onRefreshing() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addValue++;
                        responseNewsData(addValue);
                        adapter.notifyDataSetChanged();
                        lv_refresh_news.loadComplete();
                    }
                },1000);


            }
        });

    }
    private void sortOnclick(int index,Button button){
        list.clear();
        responseNewsData(addValue);
        restartButton();
        button.setTextColor(0xFFff0000);
        lv_refresh_news.setAdapter(null);

    }
    private void restartButton(){
         mainPageButton.setTextColor(0xFF000000);
         cultureButton.setTextColor(0xFF000000);
         fashionButton.setTextColor(0xFF000000);
         entertainmentButton.setTextColor(0xFF000000);
         houseButton.setTextColor(0xFF000000);
        techologyButton.setTextColor(0xFF000000);
        carButton.setTextColor(0xFF000000);
         gameButton.setTextColor(0xFF000000);
         sportButton.setTextColor(0xFF000000);
         bookButton.setTextColor(0xFF000000);
    }


    //封装数据
    private void responseNewsData(int addValue){
        //增加局部变量value，防止页面刷新切换页面时出现错误
        value = index;
        final String url = "/mainpage/androidShowPage";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index",index);
        map.put("addValue",addValue);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,map),new ResponseDataHandle(new ResponseDataListener() {

            @Override
            public void onSuccess(Object responseObj) {

                int img = 0;
                try {
                    Map<String,Object> map;
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    JSONArray newList = jsonObject.getJSONArray("newList");
                    for (int i = 0;i<newList.length();i++){
//                            map = new HashMap<String, Object>();
                            JSONObject responseList = newList.getJSONObject(i);
                            String url = responseList.getString("url");

                            int articleId = responseList.getInt("id");
                            String author = responseList.getString("author");
                            String topic = responseList.getString("topic");

                           if (value == index) {
                               if (url.equals("null")) {
                                   map = new HashMap<String, Object>();
                                   map.put("articleId", articleId);
                                   map.put(NEWS_AUTHOR, author);
                                   map.put(NEWS_TITLE, topic);
                                   Log.i("wangyan", "..." + map.size());
                                   list.add(map);
                                   getData();
                                  }else{
                                   Bitmap bitmap = null;
                                   map = new HashMap<String, Object>();
                                   map.put("articleId", articleId);
                                   map.put(NEWS_AUTHOR, author);
                                   map.put(NEWS_TITLE, topic);
                                   map.put(NEWS_IMGS,bitmap);
                                   Log.i("wangyan","not null"+"..."+list.size());
                                   responseImg(url,list.size());
                                   list.add(map);
                               }
                             }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }
    //返回图片
    private void responseImg(String url, final int i){
        Log.i("wanbyan",i+"..."+url);
        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url),new ResponseDataHandle(new ResponseDownloadListener() {
            @Override
            public void onProgress(int progress) {
            }
            @Override
            public void onSuccess(Object responseObj2) {

               Bitmap bitmap = (Bitmap) responseObj2;
                list.get(i).put(NEWS_IMGS,bitmap);
                getData();
            }
            @Override
            public void onFailure(Object reasonObj) {
            }
        }));
    }
    private void getData(){
        adapter = new RefreshAdapter(MainpageActivity.this,list);
        lv_refresh_news.setAdapter(adapter);
    }
    private void exit(AlertDialog.Builder builder){

        builder.setMessage("确定要退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExitApplication.getInstance().exitApp();
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

    }
}

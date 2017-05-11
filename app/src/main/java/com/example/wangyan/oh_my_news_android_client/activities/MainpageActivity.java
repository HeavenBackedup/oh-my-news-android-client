package com.example.wangyan.oh_my_news_android_client.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.services.MainpageService;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.JsonToObject;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.MainpageConnection;
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
    private int articalId;
    private String mainPage = "mainPage";

    private Bitmap bitmap = null;
    private int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        new Thread(new Runnable() {
            @Override
            public void run() {
                responseNewsData(index,addValue);
            }
        }).start();
        intent = getIntent();
        isLoginSuccess = intent.getBooleanExtra("isLoginSuccess",false);
        userId = intent.getIntExtra("userId",-1);
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
                intent.putExtra("mianPage",mainPage);
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
              articalId = (int) map.get("articalId");
              intent.putExtra("articalId",articalId);
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
                        responseNewsData(index,addValue);
                        adapter.notifyDataSetChanged();
                        lv_refresh_news.loadComplete();
                    }
                },1000);


            }
        });

    }
    private void sortOnclick(int index,Button button){
        list.clear();
        responseNewsData(index, addValue);
        restartButton();
        button.setTextColor(0xFF30F5A3);
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
    private void responseNewsData(int index, int addValue){
        Log.i("wangyna",index+",,index,,,,,,,,,123,,,addvalue,,,"+addValue);
        //增加局部变量value，防止页面刷新切换页面时出现错误
        value = index;
        String url = "/mainpage/showPage";
//        String url = "/mainpage/androidShowPage";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pageIndex",index);
        map.put("currentPage",addValue);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,map),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                    responseImg(responseObj);
            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }
    //后期用
    //封装数据
    private void responseNewsData1(int index,int addValue){
        value = index;
        String url = "/mainpage/androidShowPage";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index",index);
        map.put("addValue",addValue);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,map),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                String url = JsonToObject.getUrl(responseObj);
                Bitmap bitmap1 = null;
                if (url != null){
                   bitmap1 = responseImg1(url);
                }
                JsonToObject.getNews1(responseObj,bitmap1);

            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }
    //返回图片
    private Bitmap responseImg1(String url){
        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url),new ResponseDataHandle(new ResponseDownloadListener() {
            @Override
            public void onProgress(int progress) {
            }
            @Override
            public void onSuccess(Object responseObj2) {
                bitmap = (Bitmap) responseObj2;
            }
            @Override
            public void onFailure(Object reasonObj) {
            }
        }));
        return bitmap;
    }
    private void responseImg(Object responseObj){
        final Object responseObj1 = responseObj;
        String url_img = "http://cms-bucket.nosdn.127.net/catchpic/e/e8/e8af197c3b3ab1786ef430976c9ae8f3.jpg?imageView&thumbnail=550x0";
        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url_img),new ResponseDataHandle(new ResponseDownloadListener() {
            @Override
            public void onProgress(int progress) {
            }
            @Override
            public void onSuccess(Object responseObj2) {
                Map<String,Object> map;
                try {
                    JSONObject jsonObject = new JSONObject(responseObj1.toString());
                    JSONArray newList = jsonObject.getJSONArray("newList");
                    for (int i = 0;i<newList.length();i++){
                        map = new HashMap<String, Object>();
                        JSONObject responseList = newList.getJSONObject(i);
                        int articalId = responseList.getInt("id");
                        String author = responseList.getString("author");
                        String topic = responseList.getString("topic");
                        Log.i("wangyan",index+"..index..............value.."+value);
                        if (value == index) {
                            map.put("articalId", articalId);
                            map.put(NEWS_AUTHOR, author);
                            map.put(NEWS_TITLE, topic);
                            map.put(NEWS_IMGS, (Bitmap) responseObj2);
                            list.add(map);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    }
}

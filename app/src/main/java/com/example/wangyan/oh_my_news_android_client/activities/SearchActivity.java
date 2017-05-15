package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    Context context;
    private SearchView search;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ExitApplication.getInstance().addActivity(this);
        context=this;
        final LayoutInflater layoutInflater=LayoutInflater.from(context);
        search = (SearchView) findViewById(R.id.searchView);
        list = (ListView) findViewById(R.id.listView);
        LinearLayout linearLayout=(LinearLayout)layoutInflater.inflate(R.layout.itemlayout,null);
        search.setSubmitButtonEnabled(true);



        // 设置搜索文本监听
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                postRequest(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    list.setFilterText(newText);
                } else {
                    list.clearTextFilter();
                }
                return false;
            }
        });
    }
    private void postRequest(String query){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/search/showResult";
        System.out.println("0000000000");
        params.put("KeyWord",query);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    System.out.println(responseObj);
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    JSONArray resultList=jsonObject.getJSONArray("contents");

//                    JSONArray resultList=jsonObject.getJSONArray("resultList");
                    System.out.println(resultList);
                    int len= resultList.length();
                    final  List<Map<String,Object>> tt=new ArrayList<>();
                    for(int i = 0; i < len; i++){//遍历JSONArray
                        JSONObject ob= resultList.getJSONObject(i);
//                        String index=ob.getString("index");
                        String articleId=ob.getString("articleId");
                        String topic=ob.getString("topic");
//                        String title=ob.getString("title");
                        Map<String, Object> map1= new HashMap<>();
                        map1.put("articleId",articleId );
                        map1.put("topic",topic);
                        tt.add(map1);

                        // System.out.println(title);
                        // System.out.println(ob);
                    }
                    SimpleAdapter adapter=new SimpleAdapter(context,tt,R.layout.itemlayout,new String[]{"articleId","topic"},new int[]{R.id.index,R.id.text});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
                            System.out.println(arg2);
                            Map<String,Object> mm =  tt.get(arg2);
                            String index=(String )mm.get("articleId");
                            System.out.println(index);
                            Intent intent=new Intent();
                            intent.setClass(SearchActivity.this, DetailActivity.class);
                            intent.putExtra("articleId",index);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：其他错误）

                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(SearchActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(SearchActivity.this,"数据格式有误",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }
}




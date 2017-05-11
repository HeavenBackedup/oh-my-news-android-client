package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.ConcernsAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.ConcernInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;
import com.example.wangyan.oh_my_news_android_client.util.RequestBodyForm;
import com.example.wangyan.oh_my_news_android_client.util.SendOkHttpRequestPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConcernsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private Handler handler;
    private JSONArray jsonArray;
    private List<ConcernInfo> list=new ArrayList<>();
    private int length;
    private ConcernsAdapter concernsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerns);
        setTitle("我的关注");
        setBackBtn();
        Thread thread=new GetConcernsList(1);
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String line= (String) msg.getData().get("concernList");

                try {

                    jsonArray=new JSONArray(line);
                    length=jsonArray.length();
                    for (int i=0;i<length;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        ConcernInfo concernInfo=new ConcernInfo();
                        concernInfo.setAvatar((String) jsonObject.get("avatarPath"));
                        concernInfo.setNickname((String) jsonObject.get("nickName"));
                        concernInfo.setSignature((String) jsonObject.get("signature"));
                        concernInfo.setUserId((Integer) jsonObject.get("userId"));
                        list.add(concernInfo);
                        recyclerView=(RecyclerView)findViewById(R.id.concern_recycler_view);
                        final List<MultiItemOfFans> data= DataServerForHomepage.getMuliItemFansData(length);
                         concernsAdapter=new ConcernsAdapter(ConcernsActivity.this,data,list);
                        GridLayoutManager manager=new GridLayoutManager(ConcernsActivity.this,5);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(concernsAdapter);
                        concernsAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

                            @Override
                            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                                return data.get(position).getSpanSize();
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                concernsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ConcernInfo concernInfo=list.get(position/2);
                        if (position%2==0){
                            Intent intent=new Intent(ConcernsActivity.this,OthersHomepageActivity.class);
                            intent.putExtra("userId",concernInfo.getUserId());
                            intent.putExtra("nickname",concernInfo.getNickname());
                            startActivity(intent);
                        }
                    }
                });
            }
        };


    }

    public class GetConcernsList extends Thread{
        private int userId;

        public GetConcernsList(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/concernPage/getConcern";
            params.put("userId",userId);
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            RequestBody requestBody=requestBodyForm.getRequestBody();
            String updateUrl=requestBodyForm.getUrl();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String line1=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(line1);
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        String line=jsonArray.toString();
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString("concernList",line);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },requestBody);

        }
    }
}

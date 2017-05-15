package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.ConcernsAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.ConcernInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;
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

    private int length;
    private ConcernsAdapter concernsAdapter;
    private  Map<Integer,ImageView> map=new HashMap<Integer, ImageView>();
    private boolean isConcerned;
    private int userIdOfLogin;
    private int position;
    private Intent intent;
    private boolean isLoginSuccess;
    private int userIdOfShow;
    private int getConcerncode;
    public final static int CONCERNSINFO=1;
    public final static int CONCERNSSIZE=2;
    public final static int CONCERNRESULT=1;
    private int concernNum;
    public final static int FROM_CONCERN=1;
    public final static int CONCERNS_OTHER=3;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerns);
        ExitApplication.getInstance().addActivity(this);
        intent=getIntent();
        isLoginSuccess=intent.getBooleanExtra("isLoginSuccess",false);
        userIdOfLogin=intent.getIntExtra("userId",-1);
        setTitle("我的关注");
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread threadSize=new GetConcernsList(userIdOfLogin,CONCERNSSIZE);
                threadSize.start();

            }
        });
        Thread thread=new GetConcernsList(userIdOfLogin,CONCERNSINFO);
        thread.start();
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.concerns_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("fanfan refresh", String.valueOf(userIdOfLogin));
                Thread threadRefresh=new GetConcernsList(userIdOfLogin,CONCERNSINFO);
                threadRefresh.start();

            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String line= (String) msg.getData().get("concernList");
                getConcerncode=msg.getData().getInt("getConcerncode");
                final List<ConcernInfo> list=new ArrayList<>();
                switch (getConcerncode){
                    case CONCERNSINFO:
                        try {
                            jsonArray=new JSONArray(line);
                            length=jsonArray.length();
                            for (int i=0;i<length;i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                ConcernInfo concernInfo=new ConcernInfo();
                                concernInfo.setAvatar(jsonObject.getString("avatarPath"));
                                concernInfo.setNickname(jsonObject.getString("nickName"));
                                concernInfo.setSignature(jsonObject.getString("signature"));
                                concernInfo.setUserId(jsonObject.getInt("userId"));
                                concernInfo.setConcerned(true);
                                list.add(concernInfo);
                            }
                            if (list.size()==0){
                                Toast.makeText(ConcernsActivity.this,"您还没有关注别人",Toast.LENGTH_SHORT).show();
                            }
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
                            concernsAdapter.bindToRecyclerView(recyclerView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        concernsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                ConcernInfo concernInfo=list.get(position/2);
                                int index=position+1-position%2;
                                userIdOfShow=concernInfo.getUserId();
                                ImageView imageViewForHolder= (ImageView) adapter.getViewByPosition(index,R.id.fans_btn_pic);
//                                switch (position%2){
//                                    case 0:
//
//                                        break;
//                                    case 1:
//                                        break;
//                                }

                                map.put(index,imageViewForHolder);
                                Intent intent=new Intent(ConcernsActivity.this,OthersHomepageActivity.class);
                                intent.putExtra("userIdOfShow",concernInfo.getUserId());
                                intent.putExtra("nickname",concernInfo.getNickname());
                                intent.putExtra("userIdOfLogin",userIdOfLogin);
                                intent.putExtra("position",position);
                                intent.putExtra("isLoginSuccess",isLoginSuccess);
                                intent.putExtra("pageStyle",FROM_CONCERN);
                                startActivityForResult(intent,CONCERNS_OTHER);
                            }
                        });
                        concernsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    case CONCERNSSIZE:
                        try {
                            jsonArray=new JSONArray(line);
                            concernNum=jsonArray.length();
                            Intent intent=new Intent();
                            intent.putExtra("concernNum",concernNum);
                            ConcernsActivity.this.setResult(CONCERNRESULT,intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                }


            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isConcerned=data.getBooleanExtra("isConcerned",false);
        position=data.getIntExtra("position",-1);
        int index=position+1-position%2;
        ImageView imageView=map.get(index);
        Log.i("imageView", String.valueOf(map.size()));
        Log.i("index", String.valueOf(index));
        if (isConcerned==true){
            imageView.setImageResource(R.mipmap.concerned);
        }else {
            imageView.setImageResource(R.mipmap.unconcerned);
        }
    }

    public class GetConcernsList extends Thread{
        private int userId;
        private int getConcerncode;

        public GetConcernsList(int userId, int getConcerncode) {
            this.userId = userId;
            this.getConcerncode = getConcerncode;
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

                    Toast.makeText(ConcernsActivity.this," fanfan  failure",Toast.LENGTH_SHORT).show();
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
                        bundle.putInt("getConcerncode",getConcerncode);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },requestBody);

        }
    }

    @Override
    public void onBackPressed() {
        Thread threadSizeBack=new GetConcernsList(userIdOfLogin,CONCERNSSIZE);
        threadSizeBack.start();
    }
}

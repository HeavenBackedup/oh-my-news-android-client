package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.FansAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.FansInfo;
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

public class MyFansListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private Handler handler;
    private JSONArray jsonArray;
    private List<FansInfo> list=new ArrayList<>();
    private int length;
    private FansAdapter fansAdapter;
    private int userIdOfLogin;
    private int userIdOfShow;
    private Intent intent;
    private FansInfo fansInfoChanged;
    private boolean isConcerned;
    private ImageView imageView_btn;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans_list);
        ExitApplication.getInstance().addActivity(this);
        setTitle("我的粉丝");
        Log.i("index before", String.valueOf(index));
        setBackBtn();
        intent=getIntent();

        imageView_btn=(ImageView)findViewById(R.id.fans_btn_pic);
        userIdOfLogin=intent.getIntExtra("userId",-1);
        Thread thread=new GetFansList(userIdOfLogin);
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String line= (String) msg.getData().get("fansList");
                try {

                    jsonArray=new JSONArray(line);
                    length=jsonArray.length();
                         for (int i=0;i<length;i++){
                             JSONObject jsonObject=jsonArray.getJSONObject(i);
                             FansInfo fansInfo=new FansInfo();
                             Log.i("jsonObject",jsonObject.toString());
                             fansInfo.setAvatar((String) jsonObject.get("avatarPath"));
                             Log.i("setAvatar",fansInfo.getAvatar());
                             fansInfo.setNickname((String) jsonObject.get("nickName"));
                             fansInfo.setSignature((String) jsonObject.get("signature"));
                             fansInfo.setUserId((Integer) jsonObject.get("userId"));
                             if (i%2==0){
                                 fansInfo.setConcerned(true);
                             }else {
                                 fansInfo.setConcerned(false);
                             }

                             list.add(fansInfo);
                             recyclerView=(RecyclerView)findViewById(R.id.fans_list_recycler_view);
                             final List<MultiItemOfFans> data= DataServerForHomepage.getMuliItemFansData(length);
                             fansAdapter=new FansAdapter(MyFansListActivity.this,data,list);
                             GridLayoutManager manager=new GridLayoutManager(MyFansListActivity.this,5);
                             recyclerView.setLayoutManager(manager);
                             recyclerView.setAdapter(fansAdapter);
                             fansAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

                                 @Override
                                 public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                                     return data.get(position).getSpanSize();
                                 }
                             });

                         }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Log.i("onItemClick","onItemClick");
                        index=position/2;
                        Log.i("index", String.valueOf(index));
                        fansInfoChanged=list.get(position/2);
                       boolean isConcerned=fansInfoChanged.isConcerned();
                        final ImageView imageView=(ImageView) view.findViewById(R.id.fans_btn_pic);
                        if (position%2==0){
                            Intent intent=new Intent(MyFansListActivity.this,OthersHomepageActivity.class);
                            intent.putExtra("userIdOfShow",fansInfoChanged.getUserId());
                            intent.putExtra("nickname",fansInfoChanged.getNickname());
                            intent.putExtra("userIdOfLogin",userIdOfLogin);
                            intent.putExtra("codeForSkip",0);
                             startActivityForResult(intent,1);
                        }else {
//                            if (isConcerned==true){
//                                SendInfo sendInfo=new SendInfo(userIdOfLogin,userIdOfShow,2);
//                                imageView.setImageResource(R.mipmap.unconcerned);
//                                isConcerned=false;
//                            }else {
//                                SendInfo sendInfo=new SendInfo(userIdOfLogin,userIdOfShow,3);
//                                imageView.setImageResource(R.mipmap.concerned);
//                                isConcerned=true;
//                            }

                        }

                    }
                });
                Log.i("index after", String.valueOf(index));




            }
        };



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    public class GetFansList extends Thread{
        private int userId;

        public GetFansList(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/fansPage/getFans";
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
                        bundle.putString("fansList",line);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },requestBody);


        }
    }



    public class SendInfo extends Thread{
        private int userIdOfLogin;
        private int userIdOfShow;
        private int code;

        public SendInfo(int userIdOfLogin, int userIdOfShow, int code) {
            this.userIdOfLogin = userIdOfLogin;
            this.userIdOfShow = userIdOfShow;
            this.code = code;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/homePage/sendInformation";
            params.put("userIdOfLogin",userIdOfLogin);
            params.put("userIdOfShow",userIdOfShow);
            params.put("code",code);
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            String updateUrl=requestBodyForm.getUrl();
            final RequestBody requestBody=requestBodyForm.getRequestBody();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String line=response.body().string();
                    Bundle bundle=new Bundle();
                    Message message=new Message();
                    bundle.putString("line",line);
                    bundle.putString("code","isChanged");
                    bundle.putInt("codeForSend",code);
                    message.setData(bundle);
                    handler.sendMessage(message);

                }
            },requestBody);

        }
    }



}

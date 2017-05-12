package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.OthersHomepageAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;
import com.example.wangyan.oh_my_news_android_client.util.RequestBodyForm;
import com.example.wangyan.oh_my_news_android_client.util.SendOkHttpRequestPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OthersHomepageActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private Intent intent;
    private HomepageUserInfo homepageUserInfo;
    private Handler handler;
    private int userIdOfShow=2;
    private String nickname;
    private int userIdOfLogin;
    private TextView textView_con;
    private TextView textView_pri;
    private CardView cardView;
    private boolean isConcerned;
    private int codeForSend;
    private boolean isLoginSuccess=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_homepage);

        intent=getIntent();
//        userIdOfShow=intent.getIntExtra("userIdOfShow",-1);
        nickname=intent.getStringExtra("nickname");
        userIdOfLogin=intent.getIntExtra("userIdOfLogin",-1);
//        isLoginSuccess=intent.getBooleanExtra("isLoginSuccess",false);
        setTitle(nickname+"的历史文章");

        cardView=(CardView)findViewById(R.id.others_btn_cardView);
        textView_con=(TextView) findViewById(R.id.concern_unconcern_btn);
        textView_pri=(TextView) findViewById(R.id.private_msg);
        Thread thread=new GetOtherHomepageInfo(userIdOfShow);
        thread.start();
        if (isLoginSuccess==false){
            textView_con.setText("+关注");
            textView_con.setTextColor(getResources().getColor(R.color.tomato));
            textView_pri.setText("私信");
//            textView_con.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(OthersHomepageActivity.this,"请登陆后",Toast.LENGTH_SHORT).show();
//                }
//            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(OthersHomepageActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(OthersHomepageActivity.this,"请登陆后",Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            if (userIdOfLogin==userIdOfShow){
                cardView.setVisibility(View.INVISIBLE);
            }else {
                cardView.setVisibility(View.VISIBLE);
                textView_pri.setText("私信");
                Thread thread1=new GetConfirmInfo(userIdOfLogin,userIdOfLogin);
                thread1.start();
            }
        }


        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String code= (String) msg.getData().get("code");
                switch (code){
                    case "userInfo":
                        homepageUserInfo= (HomepageUserInfo) msg.getData().get("homepageUserInfo");
                        recyclerView=(RecyclerView)findViewById(R.id.others_userinfo_recycler_view);
                        final List<MultiItemOfHomepage> data= DataServerForHomepage.getMultiItemOthersData();
                        final OthersHomepageAdapter othersHomepageAdapter=new OthersHomepageAdapter(OthersHomepageActivity.this,data,homepageUserInfo);
                        final GridLayoutManager manager=new GridLayoutManager(OthersHomepageActivity.this,2);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(othersHomepageAdapter);
                        recyclerView.setAdapter(othersHomepageAdapter);
                        othersHomepageAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){

                            @Override
                            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                                return data.get(position).getSpanSize();
                            }
                        });
                        othersHomepageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Toast.makeText(OthersHomepageActivity.this,"item "+position,Toast.LENGTH_SHORT).show();
                                if (position==4){
                                    intent=new Intent(OthersHomepageActivity.this,MyArticleActivity.class);
                                    intent.putExtra("userId",homepageUserInfo.getUserId());
                                    intent.putExtra("avatarPic",homepageUserInfo.getAvatar());
                                    intent.putExtra("nickName",homepageUserInfo.getNickname());
                                    startActivity(intent);
                                }
                            }
                        });
                        break;
                    case "isConcernedInfo":
                        String line= (String) msg.getData().get("line");

                        try {
                            JSONObject jsonObject=new JSONObject(line);
                            int isConsernedNum=jsonObject.getInt("data");
                            Log.i("isConsernedNum", String.valueOf(isConsernedNum));
                            if (isConsernedNum==0){
                                isConcerned=true;
                                textView_con.setText("取消关注");
                                Log.i("取消关注", String.valueOf(isConcerned));
                                textView_con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        codeForSend=2;
                                        Thread threadDelet=new SendInfo(userIdOfLogin,userIdOfShow,2);
                                        threadDelet.start();

                                    }
                                });
                            }else {
                                isConcerned=false;
                                Log.i("添加关注", String.valueOf(isConcerned));
                                textView_con.setText("添加关注");
                                textView_con.setTextColor(getResources().getColor(R.color.tomato));
                                textView_con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        codeForSend=3;
                                        Thread threadAdd=new SendInfo(userIdOfLogin,userIdOfShow,3);
                                        threadAdd.start();
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setBackClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("isConcerned", String.valueOf(isConcerned));
                                Intent fintent=new Intent();
                                fintent.putExtra("isConcerned",isConcerned);
                                Log.i("otherisConcerned ", String.valueOf(isConcerned));
                                OthersHomepageActivity.this.setResult(2,fintent);
                                finish();
                            }
                        });
                        break;
                    case "isChanged":
                        String line1= (String) msg.getData().get("line");
                        int codeForResult=-1;
                        try {
                            JSONObject jsonObject=new JSONObject(line1);
                             codeForResult=jsonObject.getInt("code");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        codeForSend= (int) msg.getData().get("codeForSend");
                        if (codeForResult==0){
                            if (codeForSend==2){
                                isConcerned=false;
                                textView_con.setText("添加关注");
                                textView_con.setTextColor(getResources().getColor(R.color.tomato));
                                textView_con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        codeForSend=3;
                                        Thread threadAdd=new SendInfo(userIdOfLogin,userIdOfShow,3);
                                        threadAdd.start();
                                    }
                                });
                            }else {
                                isConcerned=true;
                                textView_con.setText("取消关注");
                                textView_con.setTextColor(getResources().getColor(R.color.black));
                                textView_con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        codeForSend=2;
                                        Thread threadDelet=new SendInfo(userIdOfLogin,userIdOfShow,2);
                                        threadDelet.start();
                                    }
                                });
                            }

                        }else {
                            if (codeForSend==2){
                                Toast.makeText(OthersHomepageActivity.this,"删除不成功，请重新操作",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(OthersHomepageActivity.this,"添加不成功，请重新操作",Toast.LENGTH_SHORT).show();
                            }
                        }
                        setBackClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("isConcerned", String.valueOf(isConcerned));
                                Intent fintent=new Intent();
                                fintent.putExtra("isConcerned",isConcerned);
                                Log.i("otherisConcerned ", String.valueOf(isConcerned));
                                OthersHomepageActivity.this.setResult(2,fintent);
                                finish();
                            }
                        });
                        break;
                }



            }
        };
    }

    public class GetOtherHomepageInfo extends Thread{
        private int userId;

        public GetOtherHomepageInfo(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/homePage/common";
            params.put("userId",userId);
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            String updateUrl=requestBodyForm.getUrl();
            final RequestBody requestBody=requestBodyForm.getRequestBody();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    HomepageUserInfo homepageUserInfo=new HomepageUserInfo();
                    try {
                        String line=response.body().string();
                        JSONObject jsonObject=new JSONObject(line).getJSONObject("data");
                        homepageUserInfo.setAvatar((String) jsonObject.get("avatarPath"));
                        homepageUserInfo.setUserId((Integer) jsonObject.get("usersId"));
                        homepageUserInfo.setNickname((String) jsonObject.get("nickName"));
                        homepageUserInfo.setSignature((String) jsonObject.get("signature"));
                        homepageUserInfo.setConcerns((Integer) jsonObject.get("fans"));
                        homepageUserInfo.setFans((Integer) jsonObject.get("followers"));
                        homepageUserInfo.setAnnouncement((String) jsonObject.get("announcement"));
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("homepageUserInfo",homepageUserInfo);
                        bundle.putString("code","userInfo");
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },requestBody);
        }
    }

    public class GetConfirmInfo extends Thread{
        private int userIdOfLogin;
        private int userIdOfShow;

        public GetConfirmInfo(int userIdOfLogin, int userIdOfShow) {
            this.userIdOfLogin = userIdOfLogin;
            this.userIdOfShow = userIdOfShow;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/homePage/getConfirmInfo";
            params.put("userIdOfLogin",userIdOfLogin);
            params.put("userIdOfShow",userIdOfShow);
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
                    bundle.putString("code","isConcernedInfo");
                    message.setData(bundle);;
                    handler.sendMessage(message);

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

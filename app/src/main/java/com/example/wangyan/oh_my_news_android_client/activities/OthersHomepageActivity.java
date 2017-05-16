package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;
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
    private int userIdOfShow;
    private String nickname;
    private int userIdOfLogin;
    private boolean isConcerned;
    private int codeForSend;
    private boolean isLoginSuccess;
    private int position;
    private TextView textView_con;
    private TextView textView_pri;
    private CardView cardView;
    private static final int RESULT_FANS=2;
    private int resultCode;
    public final static int RESULT_REL=1;
    public final static int RESULT_BACK=2;
    public final static int RESULT_CHANGE=4;
    private int pageStyle;
    private boolean isLoginSuccessBack;
    private int userIdOfLoginBack;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_homepage);
        ExitApplication.getInstance().addActivity(this);
        isLoginSuccess = ExitApplication.getInstance().isLoginSuccess;
        userIdOfLogin = ExitApplication.getInstance().userId;
        cardView=(CardView)findViewById(R.id.others_btn_cardView);
        textView_con=(TextView) findViewById(R.id.concern_unconcern_btn);
        textView_pri=(TextView) findViewById(R.id.private_msg);
        intent=getIntent();
        userIdOfShow=intent.getIntExtra("userIdOfShow",-1);
        pageStyle=intent.getIntExtra("pageStyle",-1);
        if (pageStyle==1){
            position=intent.getIntExtra("position",-1);
            setBackClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread threadBack=new GetConfirmInfo(userIdOfLogin,userIdOfShow,RESULT_BACK);
                    threadBack.start();
                }
            });
        }else {
            setBackBtn();
        }


        Thread thread=new GetOtherHomepageInfo(userIdOfShow);
        thread.start();
        if (isLoginSuccess==false){
            textView_con.setText("+关注");
            textView_con.setTextColor(getResources().getColor(R.color.tomato));
            textView_pri.setText("私信");
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
                cardView.setVisibility(View.GONE);
            }else {
                cardView.setVisibility(View.VISIBLE);
                textView_pri.setText("私信");
                textView_pri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(OthersHomepageActivity.this,DialogActivity.class);
                        intent.putExtra("userId",userIdOfLogin);
                        intent.putExtra("otherUserId",userIdOfShow);
                        intent.putExtra("isLoginSuccess",isLoginSuccess);
                        startActivity(intent);
                    }
                });
                Thread thread1=new GetConfirmInfo(userIdOfLogin,userIdOfShow,RESULT_REL);
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
                        nickname=homepageUserInfo.getNickname();
                        setTitle(nickname+"的主页");
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
                                    Log.i("homepage article", String.valueOf(homepageUserInfo.getUserId()));
                                    intent.putExtra("avatarPic",homepageUserInfo.getAvatar());
                                    intent.putExtra("nickName",homepageUserInfo.getNickname());
                                    startActivity(intent);
                                }
                            }
                        });
                        break;
                    case "isConcernedInfo":
                        String line= (String) msg.getData().get("line");
                        resultCode= (int) msg.getData().get("resultCode");

                        try {
                            JSONObject jsonObject = new JSONObject(line);
                            int isConsernedNum=jsonObject.getInt("data");
                            switch (resultCode){
                                case RESULT_REL:
                                    Log.i("isConsernedNum", String.valueOf(isConsernedNum));
                                    if (isConsernedNum==0){
                                        isConcerned=true;
                                        textView_con.setText("取消关注");
                                        textView_con.setTextColor(getResources().getColor(R.color.black));
                                        textView_con.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Thread threadDelet=new SendInfo(userIdOfLogin,userIdOfShow,2);
                                                threadDelet.start();

                                            }
                                        });
                                    }else {
                                        isConcerned=false;
                                        textView_con.setText("添加关注");
                                        textView_con.setTextColor(getResources().getColor(R.color.tomato));
                                        textView_con.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
//                                        codeForSend=3;
                                                Thread threadAdd=new SendInfo(userIdOfLogin,userIdOfShow,3);
                                                Log.i("userIdOfLogin other fandan", String.valueOf(userIdOfLogin));
                                                Log.i("userIdOfShow other fanfan", String.valueOf(userIdOfShow));
                                                threadAdd.start();
                                            }
                                        });

                                    }
                                    break;
                                case RESULT_BACK:
                                    Log.i("userIdOfLogin ", String.valueOf(userIdOfLogin));
                                    Log.i("userIdOfShow fanfan", String.valueOf(userIdOfShow));
                                    if (isConsernedNum==0){
                                        isConcerned=true;
                                    }else {
                                        isConcerned=false;
                                    }
                                    Intent intent=new Intent();
                                    intent.putExtra("position",position);
                                    intent.putExtra("isConcerned",isConcerned);
                                    OthersHomepageActivity.this.setResult(RESULT_CHANGE,intent);
                                    finish();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                        homepageUserInfo.setAvatar(jsonObject.getString("avatarPath"));
                        homepageUserInfo.setUserId(jsonObject.getInt("usersId"));
                        homepageUserInfo.setNickname(jsonObject.getString("nickName"));
                        homepageUserInfo.setSignature(jsonObject.getString("signature"));
                        homepageUserInfo.setConcerns(jsonObject.getInt("followers"));
                        homepageUserInfo.setFans(jsonObject.getInt("fans"));
//                        homepageUserInfo.setAnnouncement((String) jsonObject.get("announcement"));
                        homepageUserInfo.setAnnouncement(jsonObject.getString("announcement"));
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
        private int resultCode;

        public GetConfirmInfo(int userIdOfLogin, int userIdOfShow, int resultCode) {
            this.userIdOfLogin = userIdOfLogin;
            this.userIdOfShow = userIdOfShow;
            this.resultCode = resultCode;
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
                    bundle.putInt("resultCode",resultCode);
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



    @Override
    public void onBackPressed() {
        Log.i("pageStyle  fanfan otherhome stop", String.valueOf(pageStyle));
        if (pageStyle==1){
            Log.i("pageStyle backPress", String.valueOf(pageStyle));
            Thread threadDestroy=new GetConfirmInfo(userIdOfLogin,userIdOfShow,RESULT_BACK);
            threadDestroy.start();
            Log.i("pageStyle backPress", String.valueOf(pageStyle));
        }else {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLoginSuccessBack = ExitApplication.getInstance().isLoginSuccess;
        userIdOfLoginBack = ExitApplication.getInstance().userId;
        if (isLoginSuccess==false){
            if (isLoginSuccessBack=true){
                if (userIdOfLoginBack==userIdOfLogin){
                    cardView.setVisibility(View.GONE);
                }else {
                    cardView.setVisibility(View.VISIBLE);
                    textView_pri.setText("私信");
                    textView_pri.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(OthersHomepageActivity.this,DialogActivity.class);
                            intent.putExtra("userId",userIdOfLogin);
                            intent.putExtra("otherUserId",userIdOfShow);
                            intent.putExtra("isLoginSuccess",isLoginSuccess);
                            startActivity(intent);
                        }
                    });

                    Thread threadBack=new GetConfirmInfo(userIdOfLoginBack,userIdOfShow,RESULT_REL);
                    threadBack.start();
                }
                isLoginSuccess=isLoginSuccessBack;
                userIdOfLogin=userIdOfLoginBack;
            }
        }

    }
}

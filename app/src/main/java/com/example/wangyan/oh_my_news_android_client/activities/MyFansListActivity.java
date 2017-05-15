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
import android.widget.Toast;

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

public class MyFansListActivity extends BaseActivity  {
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
    private int position;
    private  Map<Integer,ImageView> map=new HashMap<>();
    private Map<Integer,ImageView> mapFan=new HashMap<>();
    private boolean isLoginSuccess;
    public final static int FANSINFO=1;
    public final static int CONCERNSIZE=2;
    public final static int CONFIRMINFO=3;
    private int getConcernCode;
    public final static int FANSRESULT=3;
    private int concernNum;
    public final static int FANS_OTHER=5;
    public final static int FROM_FANS=1;
    private int isConcernedNum;
    private int codeForSend;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans_list);
        ExitApplication.getInstance().addActivity(this);
        setTitle("我的粉丝");
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Thread threadConcernSize=new GetConcernsSize(userIdOfLogin);
                threadConcernSize.start();
            }
        });
        intent=getIntent();
        userIdOfLogin=intent.getIntExtra("userId",-1);
        isLoginSuccess=intent.getBooleanExtra("isLoginSuccess",false);
        Thread thread=new GetFansList(userIdOfLogin);
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                getConcernCode=msg.getData().getInt("getConcernCode");
                switch (getConcernCode){
                    case FANSINFO:
                        try {
                            String line= (String) msg.getData().get("fansList");
                            jsonArray=new JSONArray(line);
                            length=jsonArray.length();
                            for (int i=0;i<length;i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                FansInfo fansInfo=new FansInfo();
                                fansInfo.setAvatar((String) jsonObject.get("avatarPath"));
                                fansInfo.setNickname((String) jsonObject.get("nickName"));
                                fansInfo.setSignature((String) jsonObject.get("signature"));
                                fansInfo.setUserId((Integer) jsonObject.get("userId"));
                                fansInfo.setConcerned(jsonObject.getBoolean("concernRel"));
                                list.add(fansInfo);
                            }
                            if (list.size()==0){
                                Toast.makeText(MyFansListActivity.this,"还没有粉丝关注你呦,请多多加油吧",Toast.LENGTH_SHORT).show();
                            }
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
                            fansAdapter.bindToRecyclerView(recyclerView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        fansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                int index=position+1-position%2;
                                ImageView imageViewForHolder= (ImageView) adapter.getViewByPosition(index,R.id.fans_btn_pic);
                                fansInfoChanged=list.get(position/2);
                                map.put(index,imageViewForHolder);
                                        isConcerned=fansInfoChanged.isConcerned();
                                        Intent intent=new Intent(MyFansListActivity.this,OthersHomepageActivity.class);
                                        intent.putExtra("userIdOfShow",fansInfoChanged.getUserId());
                                        Log.i("userIdOfShow", String.valueOf(fansInfoChanged.getUserId()));
                                        intent.putExtra("nickname",fansInfoChanged.getNickname());
                                        intent.putExtra("userIdOfLogin",userIdOfLogin);
                                        intent.putExtra("position",position);
                                        intent.putExtra("isLoginSuccess",isLoginSuccess);
                                        intent.putExtra("pageStyle",FROM_FANS);
                                        Log.i("pageStyle", String.valueOf(FROM_FANS));
                                        startActivityForResult(intent,FANS_OTHER);

                            }


                        });
                        break;
                    case CONCERNSIZE:
                        try {
                            String concernList=msg.getData().getString("concernList");
                            jsonArray=new JSONArray(concernList);
                            concernNum=jsonArray.length();
                            Intent intent=new Intent();
                            intent.putExtra("concernNum",concernNum);
                            MyFansListActivity.this.setResult(FANSRESULT,intent);
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
        Log.i("fanfan myFans isConcerned", String.valueOf(isConcerned));
        if (isConcerned==true){
            imageView.setImageResource(R.mipmap.concerned);
        }else {
            imageView.setImageResource(R.mipmap.unconcerned);
        }
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
            Log.i("userId fanfan fanslist", String.valueOf(userId));
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            RequestBody requestBody=requestBodyForm.getRequestBody();
            String updateUrl=requestBodyForm.getUrl();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String line=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(line);
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        String result=jsonArray.toString();
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString("fansList",result);
                        bundle.putInt("getConcernCode",FANSINFO);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },requestBody);
        }
    }
    public class GetConcernsSize extends Thread{
        private int userId;

        public GetConcernsSize(int userId) {
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
                        bundle.putInt("getConcernCode",CONCERNSIZE);
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
        Thread threadConcernBack=new GetConcernsSize(userIdOfLogin);
        threadConcernBack.start();
    }

//
//    public class SendInfo extends Thread{
//        private int userIdOfLogin;
//        private int userIdOfShow;
//        private int position;
//        private int code;
//
//
//        public SendInfo(int userIdOfLogin, int userIdOfShow, int code, int position) {
//            this.userIdOfLogin = userIdOfLogin;
//            this.userIdOfShow = userIdOfShow;
//            this.code = code;
//            this.position = position;
//        }
//
//        public SendInfo(int userIdOfLogin, int userIdOfShow, int position) {
//            this.userIdOfLogin = userIdOfLogin;
//            this.userIdOfShow = userIdOfShow;
//            this.position = position;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            Map<String,Object> params = new HashMap<String,Object>();
//            String url="/homePage/sendInformation";
//            params.put("userIdOfLogin",userIdOfLogin);
//            params.put("userIdOfShow",userIdOfShow);
//            params.put("code",code);
//            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
//            String updateUrl=requestBodyForm.getUrl();
//            final RequestBody requestBody=requestBodyForm.getRequestBody();
//            new SendOkHttpRequestPost(updateUrl, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String line=response.body().string();
//                    try {
//                        JSONObject jsonObject=new JSONObject(line);
//                        concernNum=jsonObject.getInt("data");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    Bundle bundle=new Bundle();
//                    Message message=new Message();
//                    bundle.putInt("isConcernedNum",isConcernedNum);
//                    bundle.putInt("getConcernCode",CONFIRMINFO);
//                    bundle.putInt("codeForSend",code);
//                    bundle.putInt("position",position);
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//
//                }
//            },requestBody);
//
//        }
//    }



}

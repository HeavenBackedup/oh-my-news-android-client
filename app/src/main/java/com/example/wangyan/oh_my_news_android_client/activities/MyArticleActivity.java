package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.adapter.MyArticleAdapter;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.data.User;
import com.example.wangyan.oh_my_news_android_client.entity.ArticleInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;
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

public class MyArticleActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private User user;
    private List<ArticleInfo> list=new ArrayList<>();
    private int length;
    private Handler handler;
    private int userIdOfLogin;
    private int userIdOfShow;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_article);

        setBackBtn();
        intent=getIntent();
        user=new User();
        user.setAvatarPic(intent.getStringExtra("avatarPic"));
        user.setUserId(intent.getIntExtra("userId",1));
        user.setUserName(intent.getStringExtra("nickName"));
        Log.i("user   ",user.toString());
        setTitle(user.getUserName()+"的文章");
        Thread thread=new GetMyArticleList(user.getUserId());
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                final String line= (String) msg.getData().get("result");
                try {
                    JSONArray jsonArray=new JSONArray(line);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        ArticleInfo articleInfo=new ArticleInfo();
                        articleInfo.setArticleContent(jsonObject.getString("article"));
                        articleInfo.setTopic(jsonObject.getString("topic"));
                        articleInfo.setArticlePic("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105281826_2?Expires=2122825175&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=%2B3Q2dKk8KlxgAwkEkh8yAFxQq1o%3D");
                        if (i%2==0){
                            articleInfo.setCollectedNum(50);
                            articleInfo.setArticleId(1);
                            articleInfo.setUserId(1);
                        }else {
                            articleInfo.setCollectedNum(100);
                            articleInfo.setArticleId(4);
                            articleInfo.setArticleId(2);
                        }
                        list.add(articleInfo);

                    }
                    length=list.size();
                    recyclerView=(RecyclerView)findViewById(R.id.article_recycler_view);
                    final List<MultiItemOfCollection> data= DataServerForHomepage.getMultiItemCollectionData(length);
                    MyArticleAdapter myArticleAdapter=new MyArticleAdapter(MyArticleActivity.this,data,user,list);
                    final GridLayoutManager manager=new GridLayoutManager(MyArticleActivity.this,1);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(myArticleAdapter);
                    myArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ArticleInfo articleInfo=list.get(position/4);
                            userIdOfShow=articleInfo.getUserId();
                            int articleId=articleInfo.getArticleId();
                            userIdOfLogin=user.getUserId();
                            if (position%4==0){
                                Intent intent=new Intent(MyArticleActivity.this,OthersHomepageActivity.class);
                                intent.putExtra("userIdOfLogin",userIdOfLogin);
                                intent.putExtra("userIdOfShow",userIdOfShow);
                                startActivity(intent);
                            }else {
                                Intent intent=new Intent(MyArticleActivity.this,DetailActivity.class);
                                intent.putExtra("userIdOfShow",userIdOfShow);
                                intent.putExtra("userIdOfLogin",userIdOfLogin);
                                intent.putExtra("articleId",articleId);
                                startActivity(intent);
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
//        list=DataServerForHomepage.getArticleInfo();


//        myArticleAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup(){
//
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return data.get(position).getSpanSize();
//            }
//        });

    }

    public class GetMyArticleList extends Thread{
        private int userId;

        public GetMyArticleList(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            super.run();
            Map<String,Object> params = new HashMap<String,Object>();
            String url="/history/getContent";
            params.put("userId",userId);
            params.put("currentPage",1);
            params.put("pageItemNum",10);
            params.put("value",1);
            RequestBodyForm requestBodyForm=new RequestBodyForm(url,params);
            String updateUrl=requestBodyForm.getUrl();
            RequestBody requestBody=requestBodyForm.getRequestBody();
            new SendOkHttpRequestPost(updateUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                String line=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(line).getJSONObject("data");
                        JSONArray jsonArray=jsonObject.getJSONArray("content");
                        String result=jsonArray.toString();
                        Bundle bundle=new Bundle();
                        Message message=new Message();
                        bundle.putString("result",result);
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

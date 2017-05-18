package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.DialogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentListActivity extends AppCompatActivity {

    EditText childComment;
    Button childSubmit;
    ListView childCommentList;
    int formerCommentId;
    int articleId;
    int userId;
    Boolean isLoginSuccess;
    List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        childComment=(EditText)findViewById(R.id.met_child_comment);
        childSubmit=(Button)findViewById(R.id.child_comment_submit);
        childCommentList=(ListView)findViewById(R.id.mlv_comment_child);
        articleId=getIntent().getIntExtra("articleId",-1);
        userId=getIntent().getIntExtra("userId",-1);
        isLoginSuccess=getIntent().getBooleanExtra("isLoginSuccess",false);
        if(!isLoginSuccess){
            childComment.setVisibility(View.INVISIBLE);
            childSubmit.setVisibility(View.INVISIBLE);
        }
        Log.i("yanyue", "onCreate: "+articleId+"......."+userId+"......"+isLoginSuccess);
        formerCommentId=getIntent().getIntExtra("formerCommentId",-1);
        try {
            JSONArray comments=new JSONArray(getIntent().getStringExtra("comments"));
            showChildComments(comments);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showChildComments(JSONArray comments) {
        String title="";
        String img="";
        String info="";
        String date="";
        int replierId;
        for (int i = 0; i < comments.length(); i++) {
            JSONObject comment = null;
            try {
                comment = comments.getJSONObject(i);
                JSONObject replier = comment.getJSONObject("replier");
                title = replier.getString("name");
                img = replier.getString("userImgSrc");
                replierId=Integer.parseInt(replier.getString("userId"));
                info = comment.getString("content");
                date = comment.getString("date");

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("replierId",replierId);
                map.put("title",title);
                map.put("info",info);
                map.put("date",date);
                map.put("img",img);
                data.add(map);
                commentInit();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void commentInit(){
        CommentAdapter adapter = new CommentAdapter(this,data,R.layout.comment_list,
                new String[]{"title","info","img","date"},
                new int[]{R.id.title,R.id.info,R.id.img,R.id.date});
        childCommentList.setAdapter(adapter);
    }

    public void childSubmit(View view) {
        String comment=childComment.getText().toString();
        submit(comment);
    }

    public void submit(String msg){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/androidSubmit";

        params.put("articleId",articleId);
        params.put("userId",userId);
        params.put("type","comment");
        params.put("comment",msg);
        params.put("formerCommentId",formerCommentId);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                showMsg(responseObj);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(CommentListActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));
    }

    public void showMsg(Object responseObj){
        Boolean isSubmitSuccess=Boolean.parseBoolean(responseObj.toString());
        Log.i("yanyue", "submit....."+responseObj);
        if(isSubmitSuccess){
            DialogUtil.showDialog(CommentListActivity.this,"评论已经成功提交",false);
            childComment.setText("");
        }else{
            DialogUtil.showDialog(CommentListActivity.this,"评论提交失败，请重新提交",false);
        }
    }

    public void jump(int userIdTo) {
        Intent intent=new Intent();
        intent.putExtra("userIdOfLogin",userId);
        intent.putExtra("userIdOfShow",userIdTo);
        intent.putExtra("isLoginSuccess",isLoginSuccess);
        intent.putExtra("pageStyle",0);
        Log.i("yanyue", "jump: "+userId+"....userIdto"+userIdTo+"...."+isLoginSuccess);
        intent.setClass(this, OthersHomepageActivity.class);
        startActivity(intent);
    }

    public class CommentAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Map<String, Object>> list;
        private int layoutID;
        private String flag[];
        private int ItemIDs[];
        private Context context;
        public CommentAdapter(Context context, List<Map<String, Object>> list, int layoutID, String flag[], int ItemIDs[]) {

            this.context=context;
            this.mInflater = LayoutInflater.from(context);
            this.list = list;
            this.layoutID = layoutID;
            this.flag = flag;
            this.ItemIDs = ItemIDs;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int position1=position;
            convertView = mInflater.inflate(layoutID, null);

            TextView tv_title = (TextView) convertView.findViewById(ItemIDs[0]);
            tv_title.setText(list.get(position).get(flag[0]).toString());
            tv_title.setClickable(true);
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int replierId=Integer.parseInt(data.get(position1).get("replierId").toString());
                    jump(replierId);
                }
            });
            TextView tv_date=(TextView) convertView.findViewById(ItemIDs[3]);
            tv_date.setText(list.get(position).get(flag[3]).toString());;
            TextView tv_info=(TextView) convertView.findViewById(ItemIDs[1]);
            tv_info.setText(list.get(position).get(flag[1]).toString());;
            final ImageView iv_img=(ImageView) convertView.findViewById(ItemIDs[2]);
            Glide.with(context).load(list.get(position).get(flag[2])).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    iv_img.setImageBitmap(resource);
                }
            });
//            iv_img.setImageBitmap((Bitmap)list.get(position).get(flag[2]));

            return convertView;
        }
    }



}

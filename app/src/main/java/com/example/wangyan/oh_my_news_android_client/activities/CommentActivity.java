package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.services.DetailService;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.DialogUtil;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    EditText commentcontent;
    RadioGroup scoreRadioGroup;
    RadioButton rb0;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    RadioButton rb5;
    String score;
    int userId;
    int articleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ExitApplication.getInstance().addActivity(this);

        userId=getIntent().getIntExtra("userId",-1);
        articleId=getIntent().getIntExtra("articleId",-1);
        Log.i("yanyue", "CommentonCreate: "+userId+"......"+articleId);
        commentcontent=(EditText) findViewById(R.id.commentary);
        init();
    }

    public void init(){
        scoreRadioGroup=(RadioGroup)findViewById(R.id.mrg);
        rb0=(RadioButton)findViewById(R.id.rb0);
        rb1=(RadioButton)findViewById(R.id.rb1);
        rb2=(RadioButton)findViewById(R.id.rb2);
        rb3=(RadioButton)findViewById(R.id.rb3);
        rb4=(RadioButton)findViewById(R.id.rb4);
        rb5=(RadioButton)findViewById(R.id.rb5);
        score=rb5.getText().toString();
        scoreRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb0.getId()==checkedId){
                    score=rb0.getText().toString();
                }
                if(rb1.getId()==checkedId){
                    score=rb1.getText().toString();
                }
                if(rb2.getId()==checkedId){
                    score=rb2.getText().toString();
                }
                if(rb3.getId()==checkedId){
                    score=rb3.getText().toString();
                }if(rb4.getId()==checkedId){
                    score=rb4.getText().toString();
                }if(rb5.getId()==checkedId){
                    score=rb5.getText().toString();
                }
            }
        });


    }

    public void submit(View view) {
        String comment=commentcontent.getText().toString();
        submit("comment",comment);
        submit("score",score);
    }

    public void submit(String type, String msg){
        final String submitType=type;

        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/androidSubmit";

        params.put("articleId",articleId);
        params.put("userId",userId);
        params.put("type",type);

        switch (type){
            case "comment":
                params.put("comment",msg);
                params.put("formerCommentId",0);
                break;
            case "score":
                params.put("score",msg);
                break;
            case "reward":
                params.put("reward",msg);
                break;
        }

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                showMsg(responseObj,submitType);
            }

            @Override
            public void onFailure(Object reasonObj) {
//                Toast.makeText(CommentActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));
    }

    public void showMsg(Object responseObj,String submitType){
        Boolean isSubmitSuccess=Boolean.parseBoolean(responseObj.toString());
        Log.i("yanyue", "submit....."+responseObj);
        if(isSubmitSuccess){
            switch (submitType){
                case "comment":
                    DialogUtil.showDialog(CommentActivity.this,"评论已经成功提交",false);
                    commentcontent.setText("");
                    break;
                case "score":
                    break;
            }
        }else{
            switch (submitType){
                case "comment":
                    DialogUtil.showDialog(CommentActivity.this,"评论提交失败，请重新提交",false);
                    break;
                case "score":
                    break;
            }
        }
    }

    public void back(View view) {
        finish();
    }

    //利用service提交数据的代码，因为使用service无法弹出提示信息，所以已经弃用
//    public void CommentControl(String action,String data){
//        Intent intent=new Intent();
//        intent.putExtra("userId",userId);
//        intent.putExtra("articleId",articleId);
//        intent.putExtra("action",action);
//        intent.putExtra("data",data);
//        intent.setClass(this, DetailService.class);
//        startService(intent);
//    }


}

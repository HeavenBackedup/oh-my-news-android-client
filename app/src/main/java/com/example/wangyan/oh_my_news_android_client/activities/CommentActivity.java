package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.services.DetailService;

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

        userId=getIntent().getIntExtra("userId",-1);
        articleId=getIntent().getIntExtra("article",-1);

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
        submitComment();
        submitScore();
        commentcontent.setText("");
    }

    public void submitComment(){
        String comment=commentcontent.getText().toString();
        CommentControl("comment",comment);

    }

    public void submitScore(){
        CommentControl("score",score);
    }

    public void CommentControl(String action,String data){
        Intent intent=new Intent();
        intent.putExtra("userId",userId);
        intent.putExtra("articleId",articleId);
        intent.putExtra("action",action);
        intent.putExtra("data",data);
        intent.setClass(this, DetailService.class);
        startService(intent);
    }

    public void back(View view) {
        finish();
    }
}

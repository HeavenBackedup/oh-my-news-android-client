package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;

public class DetailActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView = (TextView)findViewById(R.id.test);
        savedInstanceState = getIntent().getExtras();
        int id= savedInstanceState.getInt("newsId");
    }
}

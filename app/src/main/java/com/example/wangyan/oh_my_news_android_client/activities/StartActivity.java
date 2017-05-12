package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.wangyan.oh_my_news_android_client.R;

public class StartActivity extends AppCompatActivity {
    private static final int LOAD_DISPLAY_TIME = 1500;
    private Handler handler;
    private Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                intent.setClass(StartActivity.this, MainpageActivity.class);
                startActivity(intent);
               StartActivity.this.finish();

            }
        }, LOAD_DISPLAY_TIME);
    }

}

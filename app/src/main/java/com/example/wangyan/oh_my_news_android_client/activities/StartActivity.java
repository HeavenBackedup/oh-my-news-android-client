package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.util.AutoLogin;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;

import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private static final int LOAD_DISPLAY_TIME = 1500;
    private Handler handler;
    private Intent intent = new Intent();
    private int userId = -1;
    private boolean isLoginSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExitApplication.getInstance().addActivity(this);

        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            public void run() {
//                AutoLogin autoLogin = new AutoLogin();
//                Map<String,Object> map = autoLogin.login();
//                userId = Integer.parseInt( map.get("userId").toString());
//                isLoginSuccess = Boolean.parseBoolean(map.get("isLoginSucess").toString());
//
//                intent.putExtra("userId",userId);
//                intent.putExtra("isLoginSuccess",isLoginSuccess);
//                intent.putExtra("type","");

                intent.setClass(StartActivity.this, MypageActivity.class);
                startActivity(intent);
               StartActivity.this.finish();

            }
        }, LOAD_DISPLAY_TIME);
    }

}

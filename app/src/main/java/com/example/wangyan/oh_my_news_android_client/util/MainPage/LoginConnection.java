package com.example.wangyan.oh_my_news_android_client.util.MainPage;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.activities.LoginActivity;
import com.example.wangyan.oh_my_news_android_client.services.LoginService;

/**
 * Created by wangyan on 2017/5/9.
 */

public class LoginConnection implements ServiceConnection {
    private LoginService loginService;
    public LoginService getLoginService(){
        return loginService;
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LoginService.Binder binder = (LoginService.Binder)service;
        loginService = binder.getLoginService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        loginService = null;
    }
}

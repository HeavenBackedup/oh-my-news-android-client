package com.example.wangyan.oh_my_news_android_client.util.MainPage;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.wangyan.oh_my_news_android_client.services.MainpageService;

/**
 * Created by wangyan on 2017/5/8.
 */

public class MainpageConnection implements ServiceConnection {
    private MainpageService mainpageService;
    public MainpageService getMainpageService(){
        return mainpageService;
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MainpageService.Binder binder = (MainpageService.Binder)service;
       mainpageService = binder.getMainpageService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

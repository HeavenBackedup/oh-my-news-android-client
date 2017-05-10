package com.example.wangyan.oh_my_news_android_client.connection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.wangyan.oh_my_news_android_client.service.HomepageService;

/**
 * Created by fanfan on 2017/5/9.
 */

public class HomepageConnection implements ServiceConnection {
    private HomepageService homepageConnection;
    public HomepageService getHomepageService(){
        return homepageConnection;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        HomepageService.Binder binder=(HomepageService.Binder)service;
        homepageConnection=binder.getHomepageService();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

package com.example.wangyan.oh_my_news_android_client.util.MainPage;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.wangyan.oh_my_news_android_client.services.RegistService;

/**
 * Created by wangyan on 2017/5/9.
 */

public class RegistConnection implements ServiceConnection{
    private RegistService registService;
    public RegistService getRegistService(){
        return registService;
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        RegistService.Binder binder = (RegistService.Binder)service;
        registService = binder.getRegistService();
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

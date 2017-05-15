package com.example.wangyan.oh_my_news_android_client.util.MainPage;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyan on 2017/5/12.
 */

public class ExitApplication extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private Map<String,Object> map = new HashMap<String, Object>();
    public Integer userId;
    public boolean isLoginSuccess;

    private static ExitApplication instance;

    private ExitApplication(){}
    public static ExitApplication getInstance(){
        if(instance == null)
            instance = new ExitApplication();
        return instance;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void exitApp(){
        for(Activity activity : activityList){
            if(activity != null)
                activity.finish();
        }
        System.exit(0);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}

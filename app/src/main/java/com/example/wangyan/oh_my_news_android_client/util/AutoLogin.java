package com.example.wangyan.oh_my_news_android_client.util;

import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanyue on 2017/5/11.
 */

public class AutoLogin {
    public  Map<String,Object> login() {
        Map<String,Object> map=new HashMap<String, Object>();
        int userId=-1;
        boolean isLoginSuccess=false;
        String filePath = Environment.getExternalStorageDirectory().toString()+ "/user.json";
        File file=new File(filePath);
        FileInputStream inputStream= null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String data="";
        String line;
        try {
            while((line=reader.readLine())!=null){
                data=data+line;
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject user=new JSONObject(data);
            userId=Integer.parseInt(user.getString("userId"));
            isLoginSuccess=Boolean.parseBoolean(user.getString("isLoginSuccess"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("yanyue", "login: "+userId+"..........."+isLoginSuccess);
        map.put("userId",userId);
        map.put("isLoginSucess",isLoginSuccess);
        return map;
    }


    public  void writeToFile(int userId,Boolean isLoginSuccess) {
        String filePath = Environment.getExternalStorageDirectory().toString()+ "/user.json";
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            JSONObject user=new JSONObject();
            user.put("userId",userId);
            user.put("isLoginSuccess",isLoginSuccess);
            fileOutputStream.write(user.toString().getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }






}

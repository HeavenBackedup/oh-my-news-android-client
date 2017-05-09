package com.example.wangyan.oh_my_news_android_client.util.MainPage;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_AUTHOR;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_ID;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_IMGS;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_TITLE;

/**
 * Created by wangyan on 2017/5/8.
 */

public class JsonToObject {
    private static boolean isLoginSuccess = false;
    private static List<Map<String, Object>> list_Maps=new ArrayList<Map<String, Object>>();

//    登陆
    public static Map<String,Object> getLogin(Object json){
        Map<String,Object> map = new HashMap<String, Object>();
        Integer userId = (Integer)json;
        if (userId == -1){
            map.put("isLoginSuccess",isLoginSuccess);
            map.put("userId",userId);
        }else {
            isLoginSuccess = true;
            map.put("isLoginSuccess",isLoginSuccess);
            map.put("userId",userId);
        }
        Log.i("wangyan","fdsfe...................."+map.toString());
        return map;
    }
//    首页
    public static List<Map<String,Object>> getNews(Object json,Object json2){
         Map<String,Object> map;
        try {
            JSONObject jsonObject = new JSONObject(json.toString());
           JSONArray newList = jsonObject.getJSONArray("newList");
            for (int i = 0;i<newList.length();i++){
                map = new HashMap<String, Object>();
                JSONObject responseList = newList.getJSONObject(i);
                int news_id = responseList.getInt("id");
                String author = responseList.getString("author");
                String topic = responseList.getString("topic");
                map.put(NEWS_ID,news_id);
                map.put(NEWS_AUTHOR,author);
                map.put(NEWS_TITLE,topic);
                map.put(NEWS_IMGS,JsonToObject.newsImgs(json2));
                list_Maps.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list_Maps;
    }
    private static Bitmap newsImgs(Object json){
        Bitmap bitmap = (Bitmap)json;
        return bitmap;
    }
}

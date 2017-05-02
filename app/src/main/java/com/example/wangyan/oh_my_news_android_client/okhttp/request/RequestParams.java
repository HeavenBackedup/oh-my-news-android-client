package com.example.wangyan.oh_my_news_android_client.okhttp.request;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangyan on 2017/4/29.
 */

/**
 * 封装请求参数
 */

public class RequestParams {
    public ConcurrentHashMap<String,Integer> integerParams = new ConcurrentHashMap<String,Integer>();
    public ConcurrentHashMap<String,String> stringParams = new ConcurrentHashMap<String, String>();
    public ConcurrentHashMap<String,Object> objectParams = new ConcurrentHashMap<String,Object>();

    /**
     * Constructs a new empty {@code RequestParams} instance.
     */
    public RequestParams() {
        this((Map<String,String>) null);
    }
    public RequestParams(Map<String,String> source){
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }

    public void put(String key,Integer value){
        if (key != null && value != null){
            integerParams.put(key,value);
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null){
            stringParams.put(key,value);
        }
    }

    public void put(String key,Object object) throws FileNotFoundException{
        if (key != null){
            objectParams.put(key,object);
        }

    }
}

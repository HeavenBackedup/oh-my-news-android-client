package com.example.wangyan.oh_my_news_android_client.classes;

/**
 * Created by leilei on 2017/5/8.
 */

public class message {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;
    private int type;

    public message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}

package com.example.wangyan.oh_my_news_android_client.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fanfan on 2017/5/6.
 */

public class MultiItemOfHomepage implements MultiItemEntity {
    public static final int HOMEPAGE_INFO=1;
    public static final int HOMEPAGE_LIST=2;
    public static final int HOMEPAGE_BTN=3;
    public static final int HOMEPAGE_TALK=4;
    public static final int BASE_SIZE=2;
    public static final int BTN_SIZE=1;
    private int itemType;
    private int spanSize;

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    private String content;

    public MultiItemOfHomepage(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public MultiItemOfHomepage(int itemType, int spanSize, String content) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = content;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}

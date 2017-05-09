package com.example.wangyan.oh_my_news_android_client.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fanfan on 2017/5/8.
 */

public class MultiItemOfCollection implements MultiItemEntity{
    public static final int COLLECTION_USER_INFO=1;
    public static final int COLLECTION_ARTICLE=2;
    public static final int COLLECTION_BTN=3;
    public static final int COLLECTION_SPAN_SIZE=2;
    public static final int COLLECTION_BTN_SIZE=1;

    private int itemType;
    private int spanSize;

    public MultiItemOfCollection(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}

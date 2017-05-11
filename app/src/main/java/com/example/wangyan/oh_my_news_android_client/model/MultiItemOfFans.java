package com.example.wangyan.oh_my_news_android_client.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by fanfan on 2017/5/8.
 */

public class MultiItemOfFans implements MultiItemEntity {
    public static final int FANS_INFO=1;
    public static final int FANS_BTN=2;
    public static final int INFO_SPAN_SIZE=4;
    public static final int BTN_SPAN_SIZE=1;

    private int itemType;
    private int spanSize;

    public MultiItemOfFans(int itemType, int spanSize) {
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

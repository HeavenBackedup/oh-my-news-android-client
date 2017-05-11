package com.example.wangyan.oh_my_news_android_client.entity;

/**
 * Created by fanfan on 2017/5/8.
 */

public class FansInfo {
    private int userId;
    private String avatar;
    private String nickname;
    private String signature;
    private boolean isConcerned;

    public boolean isConcerned() {
        return isConcerned;
    }

    public void setConcerned(boolean concerned) {
        isConcerned = concerned;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

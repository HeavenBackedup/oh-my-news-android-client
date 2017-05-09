package com.example.wangyan.oh_my_news_android_client.entity;

/**
 * Created by fanfan on 2017/5/7.
 */

public class HomepageUserInfo {

    private int userId;
    private String avatar;
    private String nickname;
    private String signature;
    private int fans;
    private int concerns;

//    public HomepageUserInfo(int avatar, String nickname, String signature, int fans, int concerns) {
//        this.avatar = avatar;
//        this.nickname = nickname;
//        this.signature = signature;
//        this.fans = fans;
//        this.concerns = concerns;
//    }

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

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getConcerns() {
        return concerns;
    }

    public void setConcerns(int concerns) {
        this.concerns = concerns;
    }
}

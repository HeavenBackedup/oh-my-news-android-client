package com.example.wangyan.oh_my_news_android_client.data;

/**
 * Created by fanfan on 2017/5/8.
 */

public class User {
    private int userId;
    private String userName;
    private String avatarPic;

    public String getAvatarPic() {
        return avatarPic;
    }

    public void setAvatarPic(String avatarPic) {
        this.avatarPic = avatarPic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}

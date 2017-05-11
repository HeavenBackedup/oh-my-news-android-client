package com.example.wangyan.oh_my_news_android_client.entity;

/**
 * Created by fanfan on 2017/5/8.
 */

public class CollectionInfo {
    private int articleId;
    private String avatar;
    private String nickname;
    private int userId;
    private String articlePic;
    private String articleContent;

    private int collectedNum;
    private String topic;

    @Override
    public String toString() {
        return "CollectionInfo{" +
                "articleId=" + articleId +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userId=" + userId +
                ", articlePic='" + articlePic + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", collectedNum=" + collectedNum +
                ", topic='" + topic + '\'' +
                '}';
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getArticlePic() {
        return articlePic;
    }

    public void setArticlePic(String articlePic) {
        this.articlePic = articlePic;
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


    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }


    public int getCollectedNum() {
        return collectedNum;
    }

    public void setCollectedNum(int collectedNum) {
        this.collectedNum = collectedNum;
    }
}

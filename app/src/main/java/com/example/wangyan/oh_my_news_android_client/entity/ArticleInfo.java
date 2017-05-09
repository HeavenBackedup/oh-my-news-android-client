package com.example.wangyan.oh_my_news_android_client.entity;

/**
 * Created by fanfan on 2017/5/8.
 */

public class ArticleInfo {
    private int articlePic;
    private String articleContent;
    private int commentNum;
    private int collectedNum;

    public int getArticlePic() {
        return articlePic;
    }

    public void setArticlePic(int articlePic) {
        this.articlePic = articlePic;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getCollectedNum() {
        return collectedNum;
    }

    public void setCollectedNum(int collectedNum) {
        this.collectedNum = collectedNum;
    }
}

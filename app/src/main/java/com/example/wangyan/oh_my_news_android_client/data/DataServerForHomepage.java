package com.example.wangyan.oh_my_news_android_client.data;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.ArticleInfo;
import com.example.wangyan.oh_my_news_android_client.entity.CollectionInfo;
import com.example.wangyan.oh_my_news_android_client.entity.UserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanfan on 2017/5/7.
 */

public class DataServerForHomepage {

    public static List<CollectionInfo> getCollectionInfo(){
        List<CollectionInfo> list=new ArrayList<>();
        CollectionInfo collectionInfo=new CollectionInfo();
        collectionInfo.setNickname("nananana");
        collectionInfo.setAvatar("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105281826_2?Expires=2122825175&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=%2B3Q2dKk8KlxgAwkEkh8yAFxQq1o%3D");
        collectionInfo.setArticleContent("nidkskd看大家发基地和父爱如供电局受到法律的回复饿范德雷克国际快递放假啊对空射击分开了阿的发的身份哈健康的回复 iu电话就放假啊客户发的社保缴费哈伦的煎豆腐");
        collectionInfo.setArticlePic(null);
        collectionInfo.setCollectedNum(19);
        collectionInfo.setUserId(1);
        collectionInfo.setTopic("fandfhadskjfhiue的法律框架的回复大发了几口丢人的法律框架上的回复 v 促销内存 v 觉得绥芬河市空间的防护林带");
        CollectionInfo collectionInfo1=new CollectionInfo();
        collectionInfo1=collectionInfo;
        CollectionInfo co2=new CollectionInfo();
        co2.setNickname("lili");
        co2.setAvatar("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105281826_2?Expires=2122825175&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=%2B3Q2dKk8KlxgAwkEkh8yAFxQq1o%3D");
        co2.setCollectedNum(50);
        co2.setArticleContent("dfdkjgireghui");
        co2.setArticleId(1);
        co2.setUserId(5);
        co2.setTopic("jddghlkdhsfusd");
        co2.setArticlePic("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105281826_2?Expires=2122825175&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=%2B3Q2dKk8KlxgAwkEkh8yAFxQq1o%3D");
        CollectionInfo co3=new CollectionInfo();
        co3.setUserId(collectionInfo.getUserId());
        co3.setTopic(collectionInfo.getTopic());
        co3.setNickname("mandjhaudd");
        co3.setArticleContent(collectionInfo.getArticleContent());
        co3.setArticleId(collectionInfo.getArticleId());
//        co3.setArticlePic(collectionInfo.getArticlePic());
        co3.setArticlePic(null);
        co3.setCollectedNum(collectionInfo.getCollectedNum());
        co3.setAvatar(collectionInfo.getAvatar());
        list.add(collectionInfo);
        list.add(collectionInfo1);
        list.add(co2);
        list.add(co3);


        return list;
    }

    public static List<ArticleInfo> getArticleInfo(){
        List<ArticleInfo> list=new ArrayList<>();
        ArticleInfo articleInfo=new ArticleInfo();
        articleInfo.setCollectedNum(20);
        articleInfo.setArticleContent("我们都是共产注意界面办事人你不烦意思但但是我们执业医章鱼曲终人散的寂寞只有上线人才现有你满足后一身红我残留在我严重单元都会发生的咖啡看见了；豆腐可加快的机会  ");
        articleInfo.setArticleId(1);
        articleInfo.setTopic("曲终人散 章鱼");
        articleInfo.setArticlePic("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105281826_2?Expires=2122825175&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=%2B3Q2dKk8KlxgAwkEkh8yAFxQq1o%3D");
        ArticleInfo articleInfo1=new ArticleInfo();
        articleInfo1.setArticlePic("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105281826_2?Expires=2122825175&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=%2B3Q2dKk8KlxgAwkEkh8yAFxQq1o%3D");
        articleInfo1.setTopic("womdndhskghd");
        articleInfo1.setArticleId(2);
        articleInfo1.setCollectedNum(50);
        articleInfo1.setArticleContent("dafkdjhgladhfuaedfkljdhfljdhf");
        ArticleInfo articleInfo2=new ArticleInfo();
        articleInfo2=articleInfo;
        ArticleInfo articleInfo3=new ArticleInfo();
        articleInfo3.setArticleContent("sksighdlgh");
        articleInfo3.setArticleId(4);
        articleInfo3.setArticlePic(null);
        articleInfo3.setCollectedNum(90);
        articleInfo3.setTopic("womendoushdifho");
        ArticleInfo articleInfo4=new ArticleInfo();
        articleInfo4=articleInfo3;
        list.add(articleInfo);
        list.add(articleInfo1);
        list.add(articleInfo2);
        list.add(articleInfo3);
        list.add(articleInfo4);
        return list;
    }

//    public static List<MultiItemOfCollection> getMuiliItemArticleData(int length){
//        List<MultiItemOfCollection> list=new ArrayList<>();
//        for (int i=0;i<length;i++){
//            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_USER_INFO));
//            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_TOPIC));
//            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_ARTICLE));
//            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_BTN));
//        }
//        return list;
//    }

    public static List<MultiItemOfCollection> getMultiItemCollectionData(int length){
        List<MultiItemOfCollection> list=new ArrayList<>();
        for (int i=0;i<length;i++){
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_USER_INFO));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_TOPIC));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_ARTICLE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_BTN));
        }

        return list;
    }

    public static List<MultiItemOfHomepage> getMultiItemData(){
        List<MultiItemOfHomepage> list=new ArrayList<>();
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_INFO,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_BTN,MultiItemOfHomepage.BTN_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_BTN,MultiItemOfHomepage.BTN_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_TALK,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        return list;
    }

    public static List<MultiItemOfHomepage> getMultiItemOthersData(){
        List<MultiItemOfHomepage> list=new ArrayList<>();
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_INFO,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_BTN,MultiItemOfHomepage.BTN_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_BTN,MultiItemOfHomepage.BTN_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_TALK,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        return list;
    }

    public static List<MultiItemOfFans> getMuliItemFansData(int length){
        List<MultiItemOfFans> list=new ArrayList<>();
        for (int i=0;i<length;i++){
            list.add(new MultiItemOfFans(MultiItemOfFans.FANS_INFO,MultiItemOfFans.INFO_SPAN_SIZE));
            list.add(new MultiItemOfFans(MultiItemOfFans.FANS_BTN,MultiItemOfFans.BTN_SPAN_SIZE));
        }
        return list;
    }


    public static UserInfo getUerInfo(){
        UserInfo userInfo=new UserInfo();
        userInfo.setNickname("fanfan");
        userInfo.setAvatar_pic(R.mipmap.ic_launcher);
        return userInfo;
    }



}

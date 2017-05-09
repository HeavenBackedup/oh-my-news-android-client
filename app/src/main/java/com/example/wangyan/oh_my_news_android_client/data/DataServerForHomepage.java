package com.example.wangyan.oh_my_news_android_client.data;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.ArticleInfo;
import com.example.wangyan.oh_my_news_android_client.entity.CollectionInfo;
import com.example.wangyan.oh_my_news_android_client.entity.FansInfo;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
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

    public static HomepageUserInfo getUserInfo(){
//
//        JSONObject jsonObject=new JSONObject();
//
//        try {
//            jsonObject.put("code",1);
//            jsonObject.put("userId",1);
//            RequestBody requestBody=requestBody=RequestBody.create(MediaType.parse("application/json"),jsonObject.toString().getBytes("UTF-8"));
//            new SendOkHttpRequestPost("",new okhttp3.Callback(){
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                   String line=response.body().string();
//                    ChangeWithJson changeWithJson=new ChangeWithJson(line);
//                    try {
//                        UserInformation userInformation=changeWithJson.changForHomepage();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            },requestBody);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        HomepageUserInfo homepageUserInfo=new HomepageUserInfo();
        homepageUserInfo.setAvatar("http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492101116270_1?Expires=1807461113&OSSAccessKeyId=LTAImvg3z9iZRy2n&Signature=6RGGw112mdxa4QdT534b%2F0ul6vQ%3D");
        homepageUserInfo.setNickname("fanfan");
        homepageUserInfo.setSignature("good day");
        homepageUserInfo.setConcerns(17);
        homepageUserInfo.setFans(19);
        return homepageUserInfo;
    }

    public static List<CollectionInfo> getCollectionInfo(){
        List<CollectionInfo> list=new ArrayList<>();
        CollectionInfo collectionInfo=new CollectionInfo();
        collectionInfo.setNickname("nananana");
        collectionInfo.setAvatar(R.mipmap.ios7_home_outline);
        collectionInfo.setArticleContent("nidkskd看大家发基地和父爱如供电局受到法律的回复饿范德雷克国际快递放假啊对空射击分开了阿的发的身份哈健康的回复 iu电话就放假啊客户发的社保缴费哈伦的煎豆腐");
        collectionInfo.setArticlePic(R.mipmap.ios_card);
        collectionInfo.setCollectedNum(19);
        collectionInfo.setCommentNum(80);
        CollectionInfo collectionInfo1=new CollectionInfo();
        collectionInfo1=collectionInfo;
        CollectionInfo co2=new CollectionInfo();
        co2=collectionInfo;
        CollectionInfo co3=new CollectionInfo();
        co3=collectionInfo;
        list.add(collectionInfo);
        list.add(collectionInfo1);
        list.add(co2);
        list.add(co3);


        return list;
    }

    public static List<ArticleInfo> getArticleInfo(){
        List<ArticleInfo> list=new ArrayList<>();
        ArticleInfo articleInfo=new ArticleInfo();
        articleInfo.setArticlePic(R.mipmap.ios7_paper_outline);
        articleInfo.setArticleContent("dgjdslhfgailudhfgjkghskjdfhglskdfh但就放假啊都发 i 人加大翻江倒海法律框架啦 地方环境亮度发哈 u 家 地方好啦空间的回复地方好老师的肌肤");
        articleInfo.setCommentNum(30);
        articleInfo.setCollectedNum(10);
        ArticleInfo articleInfo1=new ArticleInfo();
        articleInfo1=articleInfo;
        ArticleInfo articleInfo2=new ArticleInfo();
        articleInfo2=articleInfo;
        ArticleInfo articleInfo3=new ArticleInfo();
        articleInfo3=articleInfo;
        list.add(articleInfo);
        list.add(articleInfo1);
        list.add(articleInfo2);
        list.add(articleInfo3);
        return list;

    }

    public static List<MultiItemOfCollection> getMuiliItemArticleData(){
        List<MultiItemOfCollection> list=new ArrayList<>();
        for (int i=0;i<4;i++){
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_USER_INFO,MultiItemOfCollection.COLLECTION_SPAN_SIZE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_ARTICLE,MultiItemOfCollection.COLLECTION_SPAN_SIZE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_BTN,MultiItemOfCollection.COLLECTION_BTN_SIZE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_BTN,MultiItemOfCollection.COLLECTION_BTN_SIZE));
        }
        return list;
    }

    public static List<MultiItemOfCollection> getMultiItemCollectionData(){
        List<MultiItemOfCollection> list=new ArrayList<>();
        for (int i=0;i<4;i++){
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_USER_INFO,MultiItemOfCollection.COLLECTION_SPAN_SIZE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_ARTICLE,MultiItemOfCollection.COLLECTION_SPAN_SIZE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_BTN,MultiItemOfCollection.COLLECTION_BTN_SIZE));
            list.add(new MultiItemOfCollection(MultiItemOfCollection.COLLECTION_BTN,MultiItemOfCollection.COLLECTION_BTN_SIZE));
        }
        return list;
    }

//    public static List<CollectionInfo> getArticleInfo

    public static List<MultiItemOfHomepage> getMultiItemData(){
        List<MultiItemOfHomepage> list=new ArrayList<>();
//        for (int i=0;i<5;i++){
//
//        }

        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_INFO,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_BTN,MultiItemOfHomepage.BTN_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_BTN,MultiItemOfHomepage.BTN_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        list.add(new MultiItemOfHomepage(MultiItemOfHomepage.HOMEPAGE_LIST,MultiItemOfHomepage.BASE_SIZE));
        return list;
    }

    public static List<MultiItemOfFans> getMuliItemFansData(){
        List<MultiItemOfFans> list=new ArrayList<>();
        for (int i=0;i<5;i++){
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

    public static List<FansInfo> getFansInfoList(){
        List<FansInfo> list=new ArrayList<>();
        FansInfo fansInfo=new FansInfo();
        fansInfo.setNickname("fanfanfan");
        fansInfo.setSignature("jintianshigehatianqi ni kandakn ");
        fansInfo.setAvatar(R.mipmap.ios7_heart_outline);
        FansInfo fansInfo1=new FansInfo();
        fansInfo1=fansInfo;
        FansInfo fansInfo2=new FansInfo();
        fansInfo2=fansInfo;
        FansInfo fansInfo3=new FansInfo();
        fansInfo3=fansInfo;
        FansInfo fansInfo4=new FansInfo();
        fansInfo4=fansInfo;
        list.add(fansInfo);
        list.add(fansInfo1);
        list.add(fansInfo2);
        list.add(fansInfo3);
        list.add(fansInfo4);
        return list;


    }



}

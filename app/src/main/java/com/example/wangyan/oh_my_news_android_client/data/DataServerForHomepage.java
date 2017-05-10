package com.example.wangyan.oh_my_news_android_client.data;

import android.util.Log;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.ArticleInfo;
import com.example.wangyan.oh_my_news_android_client.entity.CollectionInfo;
import com.example.wangyan.oh_my_news_android_client.entity.FansInfo;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.entity.UserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fanfan on 2017/5/7.
 */

public class DataServerForHomepage {

    public static HomepageUserInfo getUserInfo(){
        final HomepageUserInfo homepageUserInfo=new HomepageUserInfo();
        Map<String,Object> params = new HashMap<String,Object>();
        String url="/homePage/common";
        params.put("userId",1);
        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj)  {
                Log.i("responseObj",responseObj.toString()+111);
                try {
                    JSONObject jsonObject=new JSONObject(responseObj.toString());
                    Log.i("responseObj",responseObj.toString()+111);
                    if (jsonObject!=null){
                        homepageUserInfo.setAvatar((String) jsonObject.get("avatarPath"));
                        homepageUserInfo.setUserId((Integer) jsonObject.get("usersId"));
                        homepageUserInfo.setNickname((String) jsonObject.get("nickName"));
                        homepageUserInfo.setSignature((String) jsonObject.get("signature"));
                        homepageUserInfo.setConcerns((Integer) jsonObject.get("fans"));
                        homepageUserInfo.setConcerns(jsonObject.getInt("followers"));
                        Log.i("homepage",homepageUserInfo.toString());

                    }
                } catch (Exception e) {
                    Log.i("exp",e.toString()+111);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.i("error",reasonObj.toString()+111);

            }
        }));

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

    public static List<MultiItemOfHomepage> getMultiItemData(){
        List<MultiItemOfHomepage> list=new ArrayList<>();
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

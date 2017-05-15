package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.data.User;
import com.example.wangyan.oh_my_news_android_client.entity.ArticleInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanfan on 2017/5/8.
 */

public class MyArticleAdapter extends BaseMultiItemQuickAdapter<MultiItemOfCollection,BaseViewHolder> {
    private Context context;
    private User user;
    private List<ArticleInfo> list=new ArrayList<>();



    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyArticleAdapter(Context context,List<MultiItemOfCollection> data,User user,List<ArticleInfo> list) {
        super(data);
        addItemType(MultiItemOfCollection.COLLECTION_USER_INFO, R.layout.collection_user_info);
        addItemType(MultiItemOfCollection.COLLECTION_ARTICLE,R.layout.collection_article);
        addItemType(MultiItemOfCollection.COLLECTION_BTN,R.layout.collection_btn);
        addItemType(MultiItemOfCollection.COLLECTION_TOPIC,R.layout.collection_topic);
        this.context=context;
        this.user=user;
        this.list=list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemOfCollection item) {
        ArticleInfo articleInfo=list.get(helper.getLayoutPosition()/4);
        switch (helper.getLayoutPosition()%4){
            case 0:
                Glide.with(context).load(user.getAvatarPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        helper.setImageBitmap(R.id.collection_avatar,resource);
                    }
                });
                helper.setText(R.id.collection_nickname,user.getUserName());
                break;
            case 2:
                if (articleInfo.getArticlePic()==null||articleInfo.getArticlePic()=="null"||articleInfo.getArticlePic()==""){
                    Log.i("pic  ",articleInfo.getArticlePic()+2);
                    helper.setVisible(R.id.collection_article_pic,false);
                }else {
                    Log.i("pic +1 ",articleInfo.getArticlePic()+1);
                    helper.setVisible(R.id.collection_article_pic,true);
                    Glide.with(context).load(articleInfo.getArticlePic()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            helper.setImageBitmap(R.id.collection_article_pic,resource);
                        }
                    });

                }
//                    helper.setImageResource(R.id.collection_article_pic, Integer.parseInt(collectionInfo.getArticlePic()));
                helper.setText(R.id.collection_article_context,articleInfo.getArticleContent());
                break;
            case 3:
                helper.setImageResource(R.id.collection_btn_pic,R.mipmap.ios7_heart_outline);
                helper.setText(R.id.collection_btn_num,String.valueOf(articleInfo.getCollectedNum()));
                break;
            case 1:
                helper.setText(R.id.collection_topic,"标题："+articleInfo.getTopic());
                break;

        }

    }
}

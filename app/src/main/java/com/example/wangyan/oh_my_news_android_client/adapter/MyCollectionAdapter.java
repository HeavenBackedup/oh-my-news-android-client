package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.CollectionInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;

import java.util.List;

/**
 * Created by fanfan on 2017/5/8.
 */

public class MyCollectionAdapter extends BaseMultiItemQuickAdapter<MultiItemOfCollection,BaseViewHolder> {
    private Context context;
    private List<CollectionInfo> list;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyCollectionAdapter(Context context, List data,List<CollectionInfo> list) {
        super(data);
        addItemType(MultiItemOfCollection.COLLECTION_USER_INFO, R.layout.collection_user_info);
        addItemType(MultiItemOfCollection.COLLECTION_ARTICLE,R.layout.collection_article);
        addItemType(MultiItemOfCollection.COLLECTION_BTN,R.layout.collection_btn);
        addItemType(MultiItemOfCollection.COLLECTION_TOPIC,R.layout.collection_topic);
        this.context=context;
        this.list=list;

    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemOfCollection item) {
        CollectionInfo collectionInfo= list.get(helper.getLayoutPosition()/4);

        switch (helper.getLayoutPosition()%4){
            case 0:
                Glide.with(context).load(collectionInfo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        helper.setImageBitmap(R.id.collection_avatar,resource);
                    }
                });
                helper.setText(R.id.collection_nickname,collectionInfo.getNickname());
                break;
            case 2:
                if (collectionInfo.getArticlePic()==null){
                    helper.setVisible(R.id.collection_article_pic,false);
                }else {
                    helper.setVisible(R.id.collection_article_pic,true);
                    Glide.with(context).load(collectionInfo.getArticlePic()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            helper.setImageBitmap(R.id.collection_article_pic,resource);
                        }
                    });

                }
//                    helper.setImageResource(R.id.collection_article_pic, Integer.parseInt(collectionInfo.getArticlePic()));
                helper.setText(R.id.collection_article_context,collectionInfo.getArticleContent());
                break;
            case 3:
                helper.setImageResource(R.id.collection_btn_pic,R.mipmap.ios7_heart_outline);
                helper.setText(R.id.collection_btn_num,String.valueOf(collectionInfo.getCollectedNum()));
                break;
            case 1:
                helper.setText(R.id.collection_topic,collectionInfo.getTopic());
                break;
        }
//        for (int i=0;i<list.size();i++){

//                Log.i("i", String.valueOf(0));
////            while (helper.getLayoutPosition()%4)
//            switch (helper.getItemViewType()){
//                case MultiItemOfCollection.COLLECTION_USER_INFO:
//                    Glide.with(context).load(collectionInfo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            helper.setImageBitmap(R.id.collection_avatar,resource);
//                        }
//                    });
////                    helper.setImageResource(R.id.collection_avatar,collectionInfo.getAvatar());
//                    helper.setText(R.id.collection_nickname,collectionInfo.getNickname());
//                    Log.i("collection_nickname",collectionInfo.getNickname());
//                    break;
//                case MultiItemOfCollection.COLLECTION_ARTICLE:
//                    Glide.with(context).load(collectionInfo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            helper.setImageBitmap(R.id.collection_article_pic,resource);
//                        }
//                    });
////                    helper.setImageResource(R.id.collection_article_pic, Integer.parseInt(collectionInfo.getArticlePic()));
//                    helper.setText(R.id.collection_article_context,collectionInfo.getArticleContent());
//                    break;
//                case MultiItemOfCollection.COLLECTION_BTN:
//                    helper.setImageResource(R.id.collection_btn_pic,R.mipmap.ios7_heart_outline);
//                    helper.setText(R.id.collection_btn_num,String.valueOf(collectionInfo.getCollectedNum()));
//                    break;
//                case MultiItemOfCollection.COLLECTION_TOPIC:
//                    helper.setText(R.id.collection_topic,collectionInfo.getTopic());
//                    break;
//            }
////        }


    }
}

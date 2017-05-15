package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fanfan on 2017/5/6.
 */

public class HomepageAdapter extends BaseMultiItemQuickAdapter<MultiItemOfHomepage,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;
    private CircleImageView circleImageView;
    private HomepageUserInfo homepageUserInfo;
    private BaseViewHolder helper;
    private Handler handler;
    private String url;
    public HomepageAdapter(Context context,List data,HomepageUserInfo homepageUserInfo) {
        super(data);
        this.context=context;
        this.homepageUserInfo=homepageUserInfo;
        addItemType(MultiItemOfHomepage.HOMEPAGE_INFO, R.layout.homepage_info_item);
        addItemType(MultiItemOfHomepage.HOMEPAGE_LIST,R.layout.homepage_item);
        addItemType(MultiItemOfHomepage.HOMEPAGE_BTN,R.layout.fans_concerns_btn);
        addItemType(MultiItemOfHomepage.HOMEPAGE_TALK,R.layout.homepage_talk);
    }



    @Override
    protected void convert(final BaseViewHolder helper, MultiItemOfHomepage item) {



        switch (helper.getItemViewType()){
            case MultiItemOfHomepage.HOMEPAGE_INFO:
//                helper.setVisible(R.id.avatar_pic,false);
                Glide.with(context).load(homepageUserInfo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        helper.setImageBitmap(R.id.avatar_pic,resource);
                    }
                });


                helper.setText(R.id.nickname_context,homepageUserInfo.getNickname());
                if (homepageUserInfo.getSignature()=="null"||homepageUserInfo.getSignature()==null){
                    helper.setText(R.id.signature_context,"简介: 您还没有介绍您自己哦");
                }else {
                    helper.setText(R.id.signature_context,"简介："+homepageUserInfo.getSignature());
                }

                break;

            case MultiItemOfHomepage.HOMEPAGE_LIST:

                switch (helper.getLayoutPosition()) {

                    case 4:
                        helper.setImageResource(R.id.list_pic, R.drawable.ic_android_favorite_outline);
                        helper.setText(R.id.list_content, "我的收藏");
//                        helper.addOnClickListener(R.id.list_content);
                        break;
                    case 5:
                        helper.setImageResource(R.id.list_pic, R.drawable.ic_ios_paper_outline);
                        helper.setText(R.id.list_content, "我的文章");
//                        helper.addOnClickListener(R.id.homepage_list);
                    break;
                    case 6:
                        helper.setImageResource(R.id.list_pic,R.drawable.ic_android_settings);
                        helper.setText(R.id.list_content,"账号设置");
//                        helper.addOnClickListener(R.id.homepage_list);
                      break;
                }
            case MultiItemOfHomepage.HOMEPAGE_BTN:
                switch (helper.getLayoutPosition()){
                    case 1:
                        helper.setText(R.id.btn,"关注");
                        helper.setText(R.id.btn_size,String.valueOf(homepageUserInfo.getConcerns()));
                        break;
                    case 2:
                        helper.setText(R.id.btn,"粉丝");
                        helper.setText(R.id.btn_size,String.valueOf(homepageUserInfo.getFans()));
                        break;
                }
                break;
            case MultiItemOfHomepage.HOMEPAGE_TALK:
                if (homepageUserInfo.getAnnouncement()==null||homepageUserInfo.getAnnouncement()=="null"){
                    helper.setText(R.id.homepage_announcement,"声明: 您还没有发表声明哦");
                }else {
                    helper.setText(R.id.homepage_announcement,"声明："+homepageUserInfo.getAnnouncement());
                }

        }


    }

}

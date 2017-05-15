package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.ConcernInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;

import java.util.List;

/**
 * Created by fanfan on 2017/5/11.
 */

public class ConcernsAdapter  extends BaseMultiItemQuickAdapter<MultiItemOfFans,BaseViewHolder> {
    private List<ConcernInfo> list;
    private Context context;
    private BaseViewHolder helper;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ConcernsAdapter(Context context,List<MultiItemOfFans> data, List<ConcernInfo> list) {
        super(data);
        addItemType(MultiItemOfFans.FANS_INFO, R.layout.fans_info);
        addItemType(MultiItemOfFans.FANS_BTN,R.layout.fans_btn);
        this.list=list;
        this.context=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemOfFans item) {
        this.helper=helper;

        ConcernInfo concernInfo=list.get(helper.getLayoutPosition()/2);
        switch (helper.getItemViewType()){
            case MultiItemOfFans.FANS_INFO:
                Glide.with(context).load(concernInfo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        helper.setImageBitmap(R.id.fans_avatar_pic,resource);
                    }
                });
                helper.setText(R.id.fans_nickname,concernInfo.getNickname());
                helper.setText(R.id.fans_signature,concernInfo.getSignature());
                break;
            case MultiItemOfFans.FANS_BTN:
                helper.setImageResource(R.id.fans_btn_pic,R.mipmap.concerned);
                break;

        }

    }
}

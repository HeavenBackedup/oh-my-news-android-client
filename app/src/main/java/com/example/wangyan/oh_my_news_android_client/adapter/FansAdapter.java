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
import com.example.wangyan.oh_my_news_android_client.entity.FansInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanfan on 2017/5/8.
 */

public class FansAdapter extends BaseMultiItemQuickAdapter<MultiItemOfFans,BaseViewHolder> {
    private List<FansInfo> list=new ArrayList<>();
    private Context context;
    private BaseViewHolder helper;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FansAdapter(Context context,List<MultiItemOfFans> data,List<FansInfo> list) {
        super(data);
        addItemType(MultiItemOfFans.FANS_INFO, R.layout.fans_info);
        addItemType(MultiItemOfFans.FANS_BTN,R.layout.fans_btn);
        this.list=list;
        this.context=context;
//        Log.i("BaseViewHolder" )
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemOfFans item) {
        this.helper=helper;

        FansInfo fansInfo=list.get(helper.getLayoutPosition()/2);
            switch (helper.getItemViewType()){
                case MultiItemOfFans.FANS_INFO:
                        Glide.with(context).load(fansInfo.getAvatar()).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                helper.setImageBitmap(R.id.fans_avatar_pic,resource);
                            }
                        });
                    helper.setText(R.id.fans_nickname,fansInfo.getNickname());
                    helper.setText(R.id.fans_signature,fansInfo.getSignature());
                    break;
                case MultiItemOfFans.FANS_BTN:
                    if (fansInfo.isConcerned()){
                        helper.setImageResource(R.id.fans_btn_pic,R.mipmap.concerned);
//                        helper.setOnClickListener(new )
                    }else {
                        helper.setImageResource(R.id.fans_btn_pic,R.mipmap.unconcerned);
                   }
                    break;

            }

    }

 public void changeForConcerned(int position,boolean isConcern){

        helper.setImageResource(R.id.fans_btn_pic,R.mipmap.unconcerned);

        if (helper.getLayoutPosition()==position){
            Log.i("getLayoutPosition", String.valueOf(helper.getLayoutPosition()));
            helper.setImageResource(R.id.fans_btn_pic,R.mipmap.unconcerned);
        }
        Log.i("position", String.valueOf(position));


    }




}

package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.FansInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfFans;

import java.util.List;

/**
 * Created by fanfan on 2017/5/8.
 */

public class FansAdapter extends BaseMultiItemQuickAdapter<MultiItemOfFans,BaseViewHolder> {
    private FansInfo fansInfo;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FansAdapter(Context context,List<MultiItemOfFans> data) {
        super(data);
        addItemType(MultiItemOfFans.FANS_INFO, R.layout.fans_info);
        addItemType(MultiItemOfFans.FANS_BTN,R.layout.fans_btn);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemOfFans item) {
        List<FansInfo> list= DataServerForHomepage.getFansInfoList();
        for (int i=0;i<5;i++){
            fansInfo=list.get(i);
            switch (helper.getItemViewType()){
                case MultiItemOfFans.FANS_INFO:
                    helper.setImageResource(R.id.fans_avatar_pic,fansInfo.getAvatar());
                    helper.setText(R.id.fans_nickname,fansInfo.getNickname());
                    helper.setText(R.id.fans_signature,fansInfo.getSignature());
                    break;
                case MultiItemOfFans.FANS_BTN:
                    helper.setImageResource(R.id.fans_btn_pic,R.mipmap.ios7_personadd_outline);
                    break;

            }
        }

    }
}

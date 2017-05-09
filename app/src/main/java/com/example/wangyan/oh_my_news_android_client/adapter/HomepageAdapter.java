package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by fanfan on 2017/5/6.
 */

public class HomepageAdapter extends BaseMultiItemQuickAdapter<MultiItemOfHomepage,BaseViewHolder>{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;
    private CircleImageView circleImageView;
    public HomepageAdapter(Context context,List data) {
        super(data);
        this.context=context;
        addItemType(MultiItemOfHomepage.HOMEPAGE_INFO, R.layout.homepage_info_item);
        addItemType(MultiItemOfHomepage.HOMEPAGE_LIST,R.layout.homepage_item);
        addItemType(MultiItemOfHomepage.HOMEPAGE_BTN,R.layout.fans_concerns_btn);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemOfHomepage item) {
        Log.i("viewHolder", String.valueOf(helper.getLayoutPosition()));
        Log.i("holder1",helper.toString());
        Log.i("holder2", String.valueOf(helper.getItemViewType()));
        Log.i("adapter", String.valueOf(helper.getAdapterPosition()));
        HomepageUserInfo homepageUserInfo= DataServerForHomepage.getUserInfo();
//        String fans= String.valueOf(homepageUserInfo.getFans());
        switch (helper.getItemViewType()){
            case MultiItemOfHomepage.HOMEPAGE_INFO:
                Log.i("viewHolderForInfo", String.valueOf(helper.getLayoutPosition()));
//                GetBitmap getBitmap=new GetBitmap(homepageUserInfo.getAvatar());
//                Bitmap bitmap=getBitmap.getBitmapFromURL();
//                helper.setImageBitmap(R.id.avatar_pic,bitmap);
//                helper.setImageResource(R.id.avatar_pic, Integer.parseInt(homepageUserInfo.getAvatar()));
                helper.setText(R.id.nickname_context,homepageUserInfo.getNickname());
                helper.setText(R.id.signature_context,homepageUserInfo.getSignature());

//                helper.setText(R.id.nickname_context,"fanfan");
//                helper.setText(R.id.signature_context,"good day!");
                break;

            case MultiItemOfHomepage.HOMEPAGE_LIST:
                Log.i("viewHolderForList", String.valueOf(helper.getLayoutPosition()));
                switch (helper.getLayoutPosition()) {
                    case 3:
                        helper.setImageResource(R.id.list_pic, R.drawable.ic_android_favorite_outline);
                        helper.setText(R.id.list_content, "我的收藏");
//                        helper.addOnClickListener(R.id.list_content);
                        break;
                    case 4:
                        helper.setImageResource(R.id.list_pic, R.drawable.ic_ios_paper_outline);
                        helper.setText(R.id.list_content, "我的文章");
//                        helper.addOnClickListener(R.id.homepage_list);
                    break;
                    case 5:
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
        }

    }
}

package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.entity.HomepageUserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfHomepage;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle.PATH;

/**
 * Created by fanfan on 2017/5/6.
 */

public class HomepageAdapter extends BaseMultiItemQuickAdapter<MultiItemOfHomepage,BaseViewHolder> implements HttpHandler{
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
    public HomepageAdapter(Context context,List data,HomepageUserInfo homepageUserInfo) {
        super(data);
        this.context=context;
        this.homepageUserInfo=homepageUserInfo;
        addItemType(MultiItemOfHomepage.HOMEPAGE_INFO, R.layout.homepage_info_item);
        addItemType(MultiItemOfHomepage.HOMEPAGE_LIST,R.layout.homepage_item);
        addItemType(MultiItemOfHomepage.HOMEPAGE_BTN,R.layout.fans_concerns_btn);
    }

    @Override
    public void handle() throws Exception {


    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemOfHomepage item) {

        switch (helper.getItemViewType()){
            case MultiItemOfHomepage.HOMEPAGE_INFO:
                Log.i("viewHolderForInfo", String.valueOf(helper.getLayoutPosition()));
                CommonOkHttpClient.downloadFile(CommonRequest.createGetResquest(homepageUserInfo.getAvatar()),new ResponseDataHandle(new ResponseDownloadListener(){

                    @Override
                    public void onSuccess(Object responseObj) {
                        Bitmap bitmap= BitmapFactory.decodeFile(((File)responseObj).getAbsolutePath());
                        helper.setImageBitmap(R.id.avatar_pic,bitmap);
                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                },PATH));

                helper.setText(R.id.nickname_context,homepageUserInfo.getNickname());
                helper.setText(R.id.signature_context,homepageUserInfo.getSignature());
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

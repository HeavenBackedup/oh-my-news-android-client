package com.example.wangyan.oh_my_news_android_client.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.data.DataServerForHomepage;
import com.example.wangyan.oh_my_news_android_client.entity.ArticleInfo;
import com.example.wangyan.oh_my_news_android_client.entity.UserInfo;
import com.example.wangyan.oh_my_news_android_client.model.MultiItemOfCollection;

import java.util.List;

/**
 * Created by fanfan on 2017/5/8.
 */

public class MyArticleAdapter extends BaseMultiItemQuickAdapter<MultiItemOfCollection,BaseViewHolder> {
    private String nickname;
    private int avart_pic;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyArticleAdapter(Context context,List<MultiItemOfCollection> data) {
        super(data);
        addItemType(MultiItemOfCollection.COLLECTION_USER_INFO, R.layout.article_user_info);
        addItemType(MultiItemOfCollection.COLLECTION_ARTICLE,R.layout.collection_article);
        addItemType(MultiItemOfCollection.COLLECTION_BTN,R.layout.collection_btn);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemOfCollection item) {
        List<ArticleInfo> list= DataServerForHomepage.getArticleInfo();
        UserInfo userInfo=DataServerForHomepage.getUerInfo();
        for (int i=0;i<4;i++){
            ArticleInfo articleInfo=list.get(i);
            switch (helper.getItemViewType()){
                case MultiItemOfCollection.COLLECTION_ARTICLE:
                    helper.setImageResource(R.id.collection_article_pic,articleInfo.getArticlePic());
                    helper.setText(R.id.collection_article_context,articleInfo.getArticleContent());
                    break;
                case MultiItemOfCollection.COLLECTION_BTN:
                    switch (helper.getLayoutPosition()%2){
                        case 0:
                            helper.setImageResource(R.id.collection_btn_pic,R.mipmap.ios7_paper_outline);
                            helper.setText(R.id.collection_btn_num,String.valueOf(articleInfo.getCommentNum()));
                            break;
                        case 1:
                            helper.setImageResource(R.id.collection_btn_pic,R.mipmap.ios7_heart_outline);
                            helper.setText(R.id.collection_btn_num,String.valueOf(articleInfo.getCollectedNum()));
                            break;
                    }
                    break;
                case MultiItemOfCollection.COLLECTION_USER_INFO:
                    helper.setImageResource(R.id.article_avatar,userInfo.getAvatar_pic());
                    helper.setText(R.id.article_nickname,userInfo.getNickname());

            }
        }

    }
}

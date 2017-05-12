package com.example.wangyan.oh_my_news_android_client.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;

/**
 * Created by wangyan on 2017/5/10.
 */

public class RefreshView extends ListView implements AbsListView.OnScrollListener {
    public static final String NO_MORE = "没有更多数据";
    public static final String LOADING = "正在加载...";
    public static final String LOADING_FAIL = "加载失败";
    public static final String LOADING_MORE = "点击加载更多";
    public static final int NO_MORE_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int LOADING_FAIL_STATE = 2;
    private static final int LOADING_COMPLETE_STATE = 3;
    private View footerView;
    private boolean isFooter;
    private ProgressBar footerProgress;
    private TextView tips;
    private int currState = LOADING_COMPLETE_STATE;
    private RefreshCallBack callBack;

    private int totalItemCount, lastVisibleItem;

    public RefreshView(Context context) {
        super(context);
        init(context);
    }
    public RefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        handleFooter(context);
        setOnScrollListener(this);
        switch (currState){
            //没有更多数据
            case NO_MORE_STATE:
                noMoreData();
                break;
            //正在加载
            case LOADING_STATE:
                loading();
                break;
            //加载失败
            case LOADING_FAIL_STATE:
                loadingFail();
                break;
            //点击加载更多
            case LOADING_COMPLETE_STATE:
                loadComplete();
                break;
        }
    }
    //处理footer
    private void handleFooter(Context context){
        footerView = LayoutInflater.from(context).inflate(R.layout.layout_listview_footer, null);
        addFooterView(footerView);
        footerProgress = (ProgressBar) footerView.findViewById(R.id.listview_footer_progress);
        tips = (TextView) footerView.findViewById(R.id.listview_footer_tips);
        footerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currState != LOADING_STATE && currState != NO_MORE_STATE) {
                    if (callBack != null) {
                        loading();
                        callBack.onRefreshing();
                    }
                }
            }
        });

    }

    //隐藏加载更多
    public void hideAddMoreView() {
        if (footerView != null)
            removeFooterView(footerView);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //SCROLL_STATE_IDLE表示屏幕已停止
        if (lastVisibleItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            if (currState != LOADING_STATE && currState != NO_MORE_STATE) {
                if (callBack != null) {
                    loading();
                    callBack.onRefreshing();
                }
            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.totalItemCount = totalItemCount;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
    }


    //没有更多数据
    public void noMoreData(){
        if (footerView != null)
            footerProgress.setVisibility(GONE);
        if (tips != null)
            tips.setText(NO_MORE);
        currState = NO_MORE_STATE;
    }
    //正在加载
    public void loading(){
        if (footerProgress != null)
            footerProgress.setVisibility(VISIBLE);
        if (tips != null)
            tips.setText(LOADING);
        currState = LOADING_STATE;
    }
    //加载失败
    public void loadingFail(){
        if (footerView != null)
            footerProgress.setVisibility(GONE);
        if (tips != null)
            tips.setText(LOADING_FAIL);
        currState = LOADING_FAIL_STATE;
    }
    //点击加载更多
    public void loadComplete(){
        currState = LOADING_COMPLETE_STATE;
        if (footerProgress != null)
            footerProgress.setVisibility(GONE);
        if (tips != null)
            tips.setText(LOADING_MORE);
    }
    //对外暴露的方法，实现接口RefreshCallBack里的方法
    public void setRefreshCallBack(RefreshCallBack callBack) {
        this.callBack = callBack;
    }
    public interface RefreshCallBack {
        void onRefreshing();
    }
    public RefreshCallBack getCallBack() {
        return callBack;
    }

}

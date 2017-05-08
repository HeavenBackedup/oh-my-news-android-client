package com.example.wangyan.oh_my_news_android_client.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;

/**
 * Created by wangyan on 2017/4/23.
 */

public class Topbar extends RelativeLayout{

    private Button leftButton,rightButton;
    private TextView tvTitle;

    private Drawable leftBackground;
    private  int leftTextColor;
    private String leftText;

    private Drawable rightBackground;
    private  int rightTextColor;
    private String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;

    private LayoutParams leftParams;
    private LayoutParams rightParams;
    private LayoutParams titleParams;

    private topbarClickListener listener;

    public interface topbarClickListener{
        public void leftClick();
        public void rightClick();
    }
    public void setOnTopbarClickListener(topbarClickListener listener){
        this.listener = listener;
    }
    /**
     *
     * @param context
     * @param attrs
     */
    public Topbar(final Context context, AttributeSet attrs) {
        super(context, attrs);

        //将自定义属性与控件关联
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        // 获取到Topbar里的自定义属性赋值给定义的变量
        leftBackground  = ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor,0);
        leftText = ta.getString(R.styleable.Topbar_leftText);

        rightBackground  = ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor,0);
        rightText = ta.getString(R.styleable.Topbar_rightText);

        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize,0);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor,0);
        title = ta.getString(R.styleable.Topbar_title);

//        将ta回收
        ta.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        //自定义属性赋给控件
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        tvTitle.setText(title);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);

        tvTitle.setGravity(Gravity.CENTER);
        setBackgroundColor(0xFFE6DADE);

        //控件以何种形式定义到viewgroup中
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(leftButton,leftParams);
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(rightButton,rightParams);
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(tvTitle,titleParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }
}

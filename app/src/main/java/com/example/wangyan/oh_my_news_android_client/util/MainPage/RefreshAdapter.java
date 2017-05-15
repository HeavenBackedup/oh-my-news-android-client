package com.example.wangyan.oh_my_news_android_client.util.MainPage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;

import java.util.List;
import java.util.Map;

import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_AUTHOR;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_IMGS;
import static com.example.wangyan.oh_my_news_android_client.services.MainpageService.NEWS_TITLE;

/**
 * Created by wangyan on 2017/5/11.
 */




public class RefreshAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;
    private List<Map<String,Object>> data;
    private static final int TYPE_1 = 0;
    private static final int TYPE_2 = 1;
    private Map<String,Object> map;

    //ViewHolder静态类
    static class ViewHolder
    {
        public ImageView iv_img;
        public TextView tv_title;
        public TextView tv_author;
    }
    static class ViewHolder2{
        public TextView tv_title2;
        public TextView tv_author2;

    }

    public RefreshAdapter(Context context,List<Map<String,Object>> data) {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
    }
    //每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        map = data.get(position);
        if (map.size() == 4){
            return TYPE_1;
        }else if (map.size() == 3){
            return TYPE_2;
        }else
            return TYPE_2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        //How many items are in the data set represented by this Adapter.
        //在此适配器中所代表的数据集中的条目数
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        //获取数据集中与指定索引对应的数据项
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
//Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id
            return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ViewHolder2 holder2 = null;
        int type = getItemViewType(position);
        //如果缓存convertView为空，则需要创建View
        if(convertView == null) {
                     //按当前所需的样式，确定new的布局
            switch (type) {
                case TYPE_1:
                    convertView = mInflater.inflate(R.layout.layout_news_show, null);
                    holder = new ViewHolder();
                    holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                    holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                    holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
                    //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                    convertView.setTag(holder);
                    break;
                case TYPE_2:
                    convertView = mInflater.inflate(R.layout.layout_news_show2,null);
                    holder2 = new ViewHolder2();
                    holder2.tv_title2 = (TextView)convertView.findViewById(R.id.tv_title2);
                    holder2.tv_author2 = (TextView)convertView.findViewById(R.id.tv_author2);
                    convertView.setTag(holder2);
                    break;

            }
        }else {
            //有convertView，按样式，取得不用的布局
            switch (type){
                case TYPE_1:
                    holder = (ViewHolder)convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2)convertView.getTag();
                    break;
            }

        }
        //设置资源
        switch (type){
            case TYPE_1:
                holder.iv_img.setImageBitmap((Bitmap) data.get(position).get(NEWS_IMGS));
                holder.tv_title.setText((String)data.get(position).get(NEWS_TITLE));
                holder.tv_author.setText((String)data.get(position).get(NEWS_AUTHOR));
                break;
            case TYPE_2:
                holder2.tv_title2.setText((String)data.get(position).get(NEWS_TITLE));
                holder2.tv_author2.setText((String)data.get(position).get(NEWS_AUTHOR));
            }
        return convertView;
    }
}

package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.services.DetailService;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.DialogUtil;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;
import com.example.wangyan.oh_my_news_android_client.util.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    TextView tv_topic;
    TextView tv_info;
    TextView tv_author;
    TextView tv_date;
    TextView tv_commentNum;
    WebView content;
    MyListView comment;
    TextView tv_more;
    Button like;
    Button collect;
    Button report;

    int articleId;
    int userId;
    boolean isLoginIn;
    boolean isLike=false;
    boolean isCollect=false;
    boolean isReport=false;

    int currentpage=1;

    String author;
    String topic;
    String htmlContent;
    int thumbupNum;
    int readed;
    int commentNum;
    String articleTime;
    float articleScore;
    int userIdOfShow;


    List<Map<String,Object>> data=new ArrayList<Map<String, Object>>();
    List<JSONArray> allComments=new ArrayList<JSONArray>();

    RewardActivity rewardActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ExitApplication.getInstance().addActivity(this);
        articleId=getIntent().getIntExtra("articleId",-1);
        userId=getIntent().getIntExtra("userId",-1);
        isLoginIn=getIntent().getBooleanExtra("isLoginSuccess",false);
        isLoginIn = ExitApplication.getInstance().isLoginSuccess;
        userId = ExitApplication.getInstance().userId;
        Log.i("yanyue", "onCreate: "+articleId+"......."+userId+"......"+isLoginIn);

        //用于跳过登陆的测试代码
//        articleId=1;
//        userId=2;
//        isLoginIn=true;

        tv_topic=(TextView)findViewById(R.id.mtv_topic);
        tv_info=(TextView)findViewById(R.id.mtv_info);
        tv_author=(TextView)findViewById(R.id.mtv_author);
        tv_date=(TextView)findViewById(R.id.mtv_date);
        tv_commentNum=(TextView)findViewById(R.id.mtv_commentNum);
        content=(WebView)findViewById(R.id.mwv_content);
        comment=(MyListView) findViewById(R.id.mlv_comment);
        tv_more=(TextView)findViewById(R.id.mtv_more);
        like=(Button)findViewById(R.id.like);
        collect=(Button)findViewById(R.id.collect);
        report=(Button)findViewById(R.id.report);

        initListenner();

        getArticle();
        getComment();
//        test();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLoginIn = ExitApplication.getInstance().isLoginSuccess;
        userId = ExitApplication.getInstance().userId;
        currentpage=1;
        data=new ArrayList<Map<String, Object>>();
        allComments=new ArrayList<JSONArray>();
        getArticle();
        getComment();
    }

    public void initListenner(){
        tv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(userIdOfShow);
            }
        });
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpage++;
                getComment();
            }
        });
        comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String formerCommentId=data.get(position).get("formerCommentId").toString();
                Log.i("yanyue", "onItemClick: "+formerCommentId);
                Intent intent=new Intent();
                intent.putExtra("userId",userId);
                intent.putExtra("articleId",articleId);
                intent.putExtra("isLoginSuccess",isLoginIn);
                intent.putExtra("formerCommentId",Integer.parseInt(formerCommentId));
                intent.putExtra("comments",allComments.get(position).toString());
                intent.setClass(DetailActivity.this,CommentListActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getArticle(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/articleReq";

        params.put("articleId",articleId);
        params.put("userId",userId);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                try {

                    JSONObject jsonObject=new JSONObject(responseObj.toString());
                    Log.i("yanyue", responseObj.toString());

                    if(jsonObject.has("article")){
                        Log.i("yanyue", "1.................................................................");
                        JSONObject article=jsonObject.getJSONObject("article");
                        Log.i("yanyue", "2.................................................................");
                        JSONObject articleInfo=article.getJSONObject("articleInfo");
                        JSONObject user=article.getJSONObject("user");
                        author=user.getString("name");
                        userIdOfShow=Integer.parseInt(user.getString("userId"));
                        topic=articleInfo.getString("topic");
                        htmlContent=articleInfo.getString("htmlContent");
                        thumbupNum=Integer.parseInt(articleInfo.getString("thumbupNum"));
                        readed=Integer.parseInt(articleInfo.getString("readed"));
                        articleTime=articleInfo.getString("articleTime");
                        commentNum=Integer.parseInt(articleInfo.getString("commentNum"));
                        articleScore=Float.parseFloat(articleInfo.getString("articleScore"));
                        Log.i("yanyue", articleInfo.toString());
                        Log.i("yanyue", commentNum+"......."+author+".."+topic+".."+thumbupNum+".."+readed+".."+articleTime+".."+articleScore+".."+htmlContent+"....."+userIdOfShow+"....");
                        Log.i("yanyue", ".................................................................");
                        articleInit();
                    }
                    if(jsonObject.has("articleReader")){
                        JSONObject articleReader=jsonObject.getJSONObject("articleReader");
                        Log.i("yanyue", articleReader.toString());
                        isCollect=Boolean.parseBoolean(articleReader.getString("collected"));
                        if(isCollect){
                            collect.setText("取消收藏");
                        }else{
                            collect.setText("收藏");
                        }
                        isLike=Boolean.parseBoolean(articleReader.getString("thumbUp"));
                        if(isLike){
                            like.setText("取消点赞");
                        }else{
                            like.setText("点赞");
                        }
                        isReport=Boolean.parseBoolean(articleReader.getString("report"));
                        if(isReport){
                            report.setText("已举报");
                            report.setClickable(false);
                        }else{
                            collect.setText("举报");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(DetailActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));

    }

    public void articleInit(){
        tv_topic.setText(topic);
        tv_info.setText("点击数："+readed+"  点赞数："+thumbupNum+"  评分："+articleScore);
        tv_author.setText("    作者："+author);
        tv_date.setText("发表时间："+articleTime);
        tv_commentNum.setText("共有"+commentNum+"条评论");
        content.loadDataWithBaseURL(null,htmlContent,"text/html", "utf-8",null);
    }

    public void getComment(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/pageReq";

        params.put("articleId",articleId);
        params.put("currentPage",currentpage);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    Log.i("yanyue", jsonObject.toString());
                    if (jsonObject.has("comments")) {
                        JSONArray rootComments=jsonObject.getJSONArray("comments");
                        if(rootComments.length()==0){
                            tv_more.setText("没有更多的评论了");
                            currentpage=currentpage-1;
                        }else{
                            tv_more.setText("点击加载更多");
                        }
                        for (int i=0;i<rootComments.length();i++){
                            JSONObject rootComment=rootComments.getJSONObject(i);
                            Log.i("yanyue", rootComment.toString());
                            JSONArray comments=rootComment.getJSONArray("comments");
                            JSONObject comment=comments.getJSONObject(0);
                            JSONObject replier=comment.getJSONObject("replier");
                            String replierId=replier.getString("userId");
                            String title=replier.getString("name");
                            String img=replier.getString("userImgSrc");
                            String info=comment.getString("content");
                            String date=comment.getString("date");
                            String formerCommentId=comment.getString("id");
                            allComments.add(comments);
                            Map<String, Object> map = new HashMap<String, Object>();
//                            Bitmap bitmap=null;
                            map.put("title",title);
                            map.put("info",info);
                            map.put("date",date);
                            map.put("img",img);
                            map.put("formerCommentId",formerCommentId);
                            map.put("replierId",replierId);
                            data.add(map);
                            commentInit();
//                            try {
//                                downloadFileOther(data.size()-1,img);
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }

                        }

                        Log.i("yanyue", "..");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(DetailActivity.this,"failure",Toast.LENGTH_LONG).show();

            }
        }));
    }

    public void commentInit(){
        CommentAdapter adapter = new CommentAdapter(this,data,R.layout.comment_list,
                new String[]{"title","info","img","date"},
                new int[]{R.id.title,R.id.info,R.id.img,R.id.date});

        comment.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(comment);
//        setListAdapter(adapter);
    }

    //登陆
    public void login(){
        Intent intent=new Intent();
        intent.putExtra("articleId",articleId);
        intent.putExtra("type","detail");
        intent.putExtra("userId",userId);
        intent.putExtra("isLoginSuccess",isLoginIn);
        intent.setClass(this,LoginActivity.class);
        startActivity(intent);
    }

    //评论
    public void comment(View view) {
        if(isLoginIn){
            Intent intent=new Intent();
            intent.putExtra("userId",userId);
            intent.putExtra("articleId",articleId);
            intent.setClass(this,CommentActivity.class);
            startActivity(intent);
        }else{
            login();
        }
    }

    //点赞
    public void like(View view) {
        if(isLoginIn){
            isLike=!isLike;
            submit("like",isLike);
        }else{
            login();
        }


    }

    //收藏
    public void collect(View view) {
        if(isLoginIn){
            isCollect=!isCollect;
            submit("collect",isCollect);
        }else{
            login();
        }

    }

    //举报
    public void report(View view) {
        if(isLoginIn){
            isReport=!isReport;
            submit("report",isReport);
        }else{
            login();
        }

    }

    //打赏
    public void reward(View view) {
        if(isLoginIn){
            rewardActivity = new RewardActivity(this,R.style.AppTheme,onClickListener);
            rewardActivity.show();
        }else{
            login();
        }


    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:
                    String reward = rewardActivity.text_reward.getText().toString().trim();
                    rewardActivity.dismiss();
                    submit("reward",reward);
                    Log.i("yanyue", reward);
                    break;
            }
        }
    };


    public void jump(int userIdTo) {
        Intent intent=new Intent();
        intent.putExtra("userIdOfLogin",userId);
        intent.putExtra("userIdOfShow",userIdTo);
        intent.putExtra("isLoginSuccess",isLoginIn);
        intent.putExtra("pageStyle",0);
        Log.i("yanyue", "jump: "+userId+"....userIdto"+userIdTo+"...."+isLoginIn);
        intent.setClass(this, OthersHomepageActivity.class);
        startActivity(intent);
    }

    //返回
    public void back(View view) {
        finish();
    }

    public void submit(String type, String msg){
        final String submitType=type;

        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/androidSubmit";

        params.put("articleId",articleId);
        params.put("userId",userId);
        params.put("type",type);

        switch (type){
            case "comment":
                params.put("comment",msg);
                break;
            case "score":
                params.put("score",msg);
                break;
            case "reward":
                params.put("reward",msg);
                break;
        }

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                showMsg(responseObj,submitType);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(DetailActivity.this,"failure",Toast.LENGTH_LONG).show();
                showMsg(false,submitType);
            }
        }));
    }
    public void submit(String type, Boolean is){
        final String submitType=type;
        final Boolean isno=is;

        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/androidSubmit";

        params.put("articleId",articleId);
        params.put("userId",userId);
        params.put("type",type);

        switch (type){
            case "report":
                params.put("report",is);
                break;
            case "like":
                params.put("like",is);
                break;
            case "collect":
                params.put("collect",is);
                break;
        }

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                showMsg(responseObj,submitType,isno);

            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(DetailActivity.this,"failure",Toast.LENGTH_LONG).show();
                showMsg(false,submitType,isno);

            }
        }));
    }

    public void showMsg(Object responseObj,String submitType){
        Boolean isSubmitSuccess=Boolean.parseBoolean(responseObj.toString());
        Log.i("yanyue", "submit....."+responseObj);
        if(isSubmitSuccess){
            switch (submitType){
                case "comment":
                    DialogUtil.showDialog(DetailActivity.this,"评论已经成功提交",false);
                    break;
                case "score":
                    break;
                case "reward":
                    DialogUtil.showDialog(DetailActivity.this,"您已打赏成功",false);
                    break;
            }
        }else{
            switch (submitType){
                case "comment":
                    DialogUtil.showDialog(DetailActivity.this,"评论提交失败，请重新提交",false);
                    break;
                case "score":
                    break;
                case "reward":
                    DialogUtil.showDialog(DetailActivity.this,"打赏失败，请重新打赏",false);
                    break;
            }
        }
    }

    public void showMsg(Object responseObj,String submitType,Boolean isno){
        Boolean isSubmitSuccess=Boolean.parseBoolean(responseObj.toString());
        Log.i("yanyue", "submit....."+responseObj);
        if(isSubmitSuccess){
            switch (submitType){
                case "report":
                    if(isno){
                        DialogUtil.showDialog(DetailActivity.this,"您已举报成功",false);
                        report.setText("已举报");
                        collect.setClickable(false);
                    }else{
                        DialogUtil.showDialog(DetailActivity.this,"您已取消举报",false);
                    }

                    break;
                case "like":
                    if(isno){
                        DialogUtil.showDialog(DetailActivity.this,"您已点赞成功",false);
                        like.setText("取消点赞");
                    }else{
                        DialogUtil.showDialog(DetailActivity.this,"您已取消点赞",false);
                        like.setText("点赞");
                    }
                    break;
                case "collect":
                    if(isno){
                        DialogUtil.showDialog(DetailActivity.this,"您已收藏成功",false);
                        collect.setText("取消收藏");
                    }else{
                        DialogUtil.showDialog(DetailActivity.this,"您已取消收藏",false);
                        collect.setText("收藏");
                    }
                    break;
            }
        }else{
            switch (submitType){
                case "report":
                    DialogUtil.showDialog(DetailActivity.this,"举报提交失败",false);
                    break;
                case "like":
                    DialogUtil.showDialog(DetailActivity.this,"点赞提交失败",false);
                    break;
                case "collect":
                    DialogUtil.showDialog(DetailActivity.this,"收藏提交是失败",false);
                    break;
            }
        }
    }


    public class CommentAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Map<String, Object>> list;
        private int layoutID;
        private String flag[];
        private int ItemIDs[];
        private Context context;

        public CommentAdapter(Context context, List<Map<String, Object>> list, int layoutID, String flag[], int ItemIDs[]) {

            this.context=context;
            this.mInflater = LayoutInflater.from(context);
            this.list = list;
            this.layoutID = layoutID;
            this.flag = flag;
            this.ItemIDs = ItemIDs;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int position1=position;
            convertView = mInflater.inflate(layoutID, null);

            TextView tv_title = (TextView) convertView.findViewById(ItemIDs[0]);
            tv_title.setText(list.get(position).get(flag[0]).toString());
            tv_title.setClickable(true);
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int replierId=Integer.parseInt(data.get(position1).get("replierId").toString());
                    jump(replierId);
                }
            });
            TextView tv_date=(TextView) convertView.findViewById(ItemIDs[3]);
            tv_date.setText(list.get(position).get(flag[3]).toString());;
            TextView tv_info=(TextView) convertView.findViewById(ItemIDs[1]);
            tv_info.setText(list.get(position).get(flag[1]).toString());;
            final ImageView iv_img=(ImageView) convertView.findViewById(ItemIDs[2]);
            Glide.with(context).load(list.get(position).get(flag[2])).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    iv_img.setImageBitmap(resource);
                }
            });
//            iv_img.setImageBitmap((Bitmap)list.get(position).get(flag[2]));

            return convertView;
        }
    }



    //用于加载图片的代码，但由于占用内存过多且速度慢，已经取消
//    private void downloadFileOther(int i,String img) throws FileNotFoundException {
////        String url = "http://cms-bucket.nosdn.127.net/catchpic/e/e8/e8af197c3b3ab1786ef430976c9ae8f3.jpg?imageView&thumbnail=550x0";
//        String url=img;
//        final int index=i;
//        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url),new ResponseDataHandle(new ResponseDownloadListener() {
//
//            @Override
//            public void onProgress(int progress) {
////           下载进度已封装，可根据需求实现
//            }
//            @Override
//            public void onSuccess(Object responseObj) {
//
//                Bitmap bitmap;
//                bitmap = (Bitmap) responseObj;
//                data.get(index).put("img",bitmap);
////                map.put("img",bitmap);
////                data.add(map);
//                commentInit();
//            }
//
//            @Override
//            public void onFailure(Object reasonObj) {
////                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：io错误）
//                OkHttpException exception = new OkHttpException();
//                if (exception.getEcode() == -1 && exception.getEmsg() == null){
//                    Toast.makeText(DetailActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
//                }
//                if (exception.getEcode() == -2 && exception.getEmsg() == null){
//                    Toast.makeText(DetailActivity.this,"文件不存在",Toast.LENGTH_LONG).show();
//                }
//            }
//        }));
//    }


    //利用service提交数据的代码，因为使用service无法弹出提示信息，所以已经弃用
//    public void detailControl(String action,Boolean data){
//        Intent intent=new Intent();
//        intent.putExtra("userId",userId);
//        intent.putExtra("articleId",articleId);
//        intent.putExtra("action",action);
//        intent.putExtra("data",data);
//        intent.setClass(this, DetailService.class);
//        startService(intent);
//    }
//
//    public void detailControl(String action,String data){
//        Intent intent=new Intent();
//        intent.putExtra("userId",userId);
//        intent.putExtra("articleId",articleId);
//        intent.putExtra("action",action);
//        intent.putExtra("data",data);
//        intent.setClass(this, DetailService.class);
//        startService(intent);
//    }

    //    //用于测试免登陆功能
//    public void test(){
//        AutoLogin l=new AutoLogin();
//        l.writeToFile(1,true);
//        Map<String,Object> m=l.login();
//        l.writeToFile(2,false);
//        m=l.login();
//    }

//      //使用设置listview的高度来解决listview显示不全的问题。但是当list的项高度不同的时候
//      //这个方法就失效了。采用重写一个MyListView的方法
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        // 获取ListView对应的Adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//            // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            // 计算子项View 的宽高
//            listItem.measure(0, 0);
//            // 统计所有子项的总高度
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        // listView.getDividerHeight()获取子项间分隔符占用的高度
//        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
//    }

//      //用于测试的数据
//    public void dataTest(){
//        author="闫玥";
//        topic="国家主席习近平14日在人民大会堂会见圣多美和普林西比总理特罗瓦达。";
////        htmlContent="<p><img src=\\\"http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492101116270_1?Expires=1807461113&amp;OSSAccessKeyId=LTAImvg3z9iZRy2n&amp;Signature=6RGGw112mdxa4QdT534b%2F0ul6vQ%3D\\\" style=\\\"width: 25%;float: left;\\\"/>\\u200B</p><p style=\\\"font-size: 16px;text-align: justify;\\\">新华社北京4月14日电 国家主席习近平14日在人民大会堂会见圣多美和普林西比总理特罗瓦达。</p><p style=\\\"font-size: 16px;text-align: justify;\\\">习近平指出，中圣普关系揭开了新的一页，圣普重回中非友好合作大家庭，顺应了当今时代潮流。中方高度赞赏总理先生为实现中圣普关系正常化作出的重要贡献，愿同圣普方一道，共同推进两国各领域友好互利合作，推动构建平等互信、合作共赢的全面合作伙伴关系。</p><p style=\\\"font-size: 16px;text-align: justify;\\\">习近平强调，双方要深化政治互信，密切两国政府、立法机构、政党等各领域交流与合作，在涉及彼此核心利益和重大关切问题上相互理解和支持。双方要拓展务实合作。中方愿优先支持圣普完善国家全面发展规划，着力开展旅游业、渔业、农业三大领域互利合作，帮助圣普构筑基础设施建设、人力资源开发和安全能力建设三大支柱保障，实现自主可持续发展。双方要积极开展教育、文化、医疗卫生、智库、媒体、青年、妇女等人文领域交流合作，不断增进两国人民之间相互了解和友谊，巩固两国友好民意和社会基础。中方愿同圣普方在打击海盗和跨国有组织犯罪等安全领域开展交流合作，就联合国2030年可持续发展议程、气候变化、非洲和平与发展等国际和地区问题加强沟通和协调。</p><p style=\\\"font-size: 16px;text-align: justify;\\\"><br/></p><p style=\\\"font-size: 16px;text-align: justify;\\\"><img src=\\\"http://oh-my-news.oss-cn-shanghai.aliyuncs.com/1492105228958_1?Expires=2122825175&amp;OSSAccessKeyId=LTAImvg3z9iZRy2n&amp;Signature=3gA%2BJohAw18jKqhHSCoyhgMj9MQ%3D\\\" style=\\\"width: 25%;float: right;\\\"/>\\u200B</p><p style=\\\"font-size: 16px;text-align: justify;\\\">习近平指出，我提出发展中非关系要秉持真实亲诚的政策理念和正确义利观，其核心要义就是把中国发展同助力非洲发展紧密结合起来，实现互利共赢。中方愿支持非洲致力于联合自强和发展振兴，实现自主可持续发展，携手推进构建中非命运共同体和利益共同体。</p><p style=\\\"font-size: 16px;text-align: justify;\\\">特罗瓦达表示，圣普人民同中国人民的友谊源远流长。圣普人民从来没有忘记中国人民给予的帮助和支持。与中国复交在圣普受到广泛支持。圣普政府坚定奉行一个中国政策，高度赞赏中国真实亲诚的对非政策，愿本着互尊互信的精神，增进同中方高层交往，加强经贸、基础设施、旅游、安全等领域合作，深化非洲同中国的传统友谊，密切在国际事务中相互支持。</p><p style=\\\"font-size: 16px;text-align: justify;\\\">国务委员杨洁篪等参加会见。</p>";
//        htmlContent="<p>四  海一家餐厅适合各个年龄、层次、性质的用餐需求。除了食物给予得丰盛、美味，为了营造舒适的用餐氛围，特别细心、体贴地做出了空间划分：温馨浪漫的情侣就  餐区；热   闹欢庆的朋友相聚区；商务宴请的VIP餐区；红酒美食品鉴区；公司PARTY聚餐区；婚宴喜庆区等。餐厅根据不同的需求还将做出相应布置的调整，用餐的同   时感受到环境给予的美好记忆和回味。</p>  \n" +
//                "<p><img src=\"http://d2.lashouimg.com/wg/shenzhen/201304/10/shyjsz11.jpg\" alt=\"1\" width=\"640\" height=\"252\" hspace=\"0\" vspace=\"0\" border=\"0\" title=\"拉手图\" /></p>  \n" +
//                "<p> </p>  \n" +
//                "<p> </p>  ";
//        thumbupNum=50;
//        readed=100;
//        articalTime="20120303";
//        articalScore=1.5f;
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("title","AAA");
//        map.put("info","kdjifowehfugeid");
//        map.put("img",R.drawable.newsphoto);
//        data.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title","BBB");
//        map.put("info","kdjifowehfugeid");
//        map.put("img",R.drawable.newsphoto);
//        data.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("title","CCC");
//        map.put("info","kdjifowehfugeid");
//        map.put("img",R.drawable.newsphoto);
//        data.add(map);
//
//    }



//    //分享
//    public void share(View view) {
//    }
}

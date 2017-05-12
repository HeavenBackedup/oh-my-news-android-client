package com.example.wangyan.oh_my_news_android_client.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDownloadListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.services.DetailService;
import com.example.wangyan.oh_my_news_android_client.util.AutoLogin;
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
    String articalTime;
    float articalScore;
    int userIdOfShow;


    List<Map<String, ?>> data=new ArrayList<Map<String, ?>>();

    Map<String,List<Map<String, ?>>> childComment=new HashMap<String,List<Map<String, ?>>>();

    RewardActivity rewardActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ExitApplication.getInstance().addActivity(this);
        articleId=getIntent().getIntExtra("articleId",-1);
        userId=getIntent().getIntExtra("userId",-1);
        isLoginIn=getIntent().getBooleanExtra("isLoginSuccess",true);
        Log.i("yanyue", "onCreate: "+articleId+"......."+userId+"......"+isLoginIn);

        tv_topic=(TextView)findViewById(R.id.mtv_topic);
        tv_info=(TextView)findViewById(R.id.mtv_info);
        tv_author=(TextView)findViewById(R.id.mtv_author);
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

    public void initListenner(){
        tv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
            }
        });
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpage++;
                getComment();
            }
        });
//        comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }



    public void getArticle(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/articalReq";

        params.put("articalId",articleId);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                try {

                    JSONObject jsonObject=new JSONObject(responseObj.toString());
                    Log.i("yanyue", responseObj.toString());

                    if(jsonObject.has("artical")){
                        Log.i("yanyue", "1.................................................................");
                        JSONObject artical=jsonObject.getJSONObject("artical");
                        Log.i("yanyue", "2.................................................................");
                        JSONObject articalInfo=artical.getJSONObject("articalInfo");
                        JSONObject user=artical.getJSONObject("user");
                        Log.i("yanyue", "2.................................................................");
                        author=user.getString("name");
                        userIdOfShow=Integer.parseInt(user.getString("userId"));
                        topic=articalInfo.getString("topic");
                        htmlContent=articalInfo.getString("htmlContent");
                        thumbupNum=Integer.parseInt(articalInfo.getString("thumbupNum"));
                        readed=Integer.parseInt(articalInfo.getString("readed"));
                        articalTime=articalInfo.getString("articalTime");
                        articalScore=Float.parseFloat(articalInfo.getString("articalScore"));
                        Log.i("yanyue", author+".."+topic+".."+thumbupNum+".."+readed+".."+articalTime+".."+articalScore+".."+htmlContent+"....."+userIdOfShow+"....");
                        Log.i("yanyue", ".................................................................");
                        articleInit();
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
        tv_info.setText("时间："+articalTime+"  点击数："+readed+"  点赞数："+thumbupNum+"  评分："+articalScore);
        tv_author.setText(author);
        content.loadDataWithBaseURL(null,htmlContent,"text/html", "utf-8",null);
    }

    public void getComment(){
        Map<String,Object> params = new HashMap<String,Object>();
        String url = "/detail/pageReq";

//        params.put("articalId",articleId);
        params.put("currentPage",currentpage);

        CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    Log.i("yanyue", jsonObject.toString());
                    if (jsonObject.has("comments")) {
                        JSONArray rootComments=jsonObject.getJSONArray("comments");
                        for (int i=0;i<rootComments.length();i++){
                            JSONObject rootComment=rootComments.getJSONObject(i);
                            JSONArray comments=rootComment.getJSONArray("comments");
                            JSONObject comment=comments.getJSONObject(0);
                            JSONObject replier=comment.getJSONObject("replier");
                            String title=replier.getString("name");
                            String img=replier.getString("userImgSrc");
                            String info=comment.getString("content");
                            String date=comment.getString("date");

                            try {
                                downloadFileOther(title,info,img,date);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

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
        SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.comment_list,
                new String[]{"title","info","img","date"},
                new int[]{R.id.title,R.id.info,R.id.img,R.id.date});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        comment.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(comment);
//        setListAdapter(adapter);
    }

    private void downloadFileOther(String title,String info,String img,String date) throws FileNotFoundException {
        String url = "http://cms-bucket.nosdn.127.net/catchpic/e/e8/e8af197c3b3ab1786ef430976c9ae8f3.jpg?imageView&thumbnail=550x0";
//        String url=img;

        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("title",title);
        map.put("info",info);
        map.put("date",date);
        CommonOkHttpClient.downloadFileOther(CommonRequest.createGetResquest(url),new ResponseDataHandle(new ResponseDownloadListener() {

            @Override
            public void onProgress(int progress) {
//           下载进度已封装，可根据需求实现
            }
            @Override
            public void onSuccess(Object responseObj) {

                Bitmap bitmap;
                bitmap = (Bitmap) responseObj;
                map.put("img",bitmap);
                data.add(map);
                commentInit();
            }

            @Override
            public void onFailure(Object reasonObj) {
//                自定义异常，当网络请求失败时可能需要在页面进行显示（-1：网络错误；-2：io错误）
                OkHttpException exception = new OkHttpException();
                if (exception.getEcode() == -1 && exception.getEmsg() == null){
                    Toast.makeText(DetailActivity.this,"网络不稳定",Toast.LENGTH_LONG).show();
                }
                if (exception.getEcode() == -2 && exception.getEmsg() == null){
                    Toast.makeText(DetailActivity.this,"文件不存在",Toast.LENGTH_LONG).show();
                }
            }
        }));
    }

    public void detailControl(String action,Boolean data){
        Intent intent=new Intent();
        intent.putExtra("userId",userId);
        intent.putExtra("articleId",articleId);
        intent.putExtra("action",action);
        intent.putExtra("data",data);
        intent.setClass(this, DetailService.class);
        startService(intent);
    }

    public void detailControl(String action,String data){
        Intent intent=new Intent();
        intent.putExtra("userId",userId);
        intent.putExtra("articleId",articleId);
        intent.putExtra("action",action);
        intent.putExtra("data",data);
        intent.setClass(this, DetailService.class);
        startService(intent);
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
            detailControl("like",isLike);
            if(isLike){
                like.setText("取消点赞");
            }else{
                like.setText("点赞");
            }
        }else{
            login();
        }


    }

    //收藏
    public void collect(View view) {
        if(isLoginIn){
            isCollect=!isCollect;
            detailControl("collect",isCollect);
            if(isCollect){
                collect.setText("取消收藏");
            }else{
                collect.setText("收藏");
            }
        }else{
            login();
        }

    }

    //举报
    public void report(View view) {
        if(isLoginIn){
            isReport=!isReport;
            detailControl("report",isReport);
            report.setClickable(false);
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
                    detailControl("reward",reward);
                    Log.i("yanyue", reward);
                    break;
            }
        }
    };


    public void jump() {
        Intent intent=new Intent();
        intent.putExtra("userIdOfLogin",userId);
        intent.putExtra("userIdOfShow",userIdOfShow);
        intent.putExtra("isLoginSuccess",isLoginIn);
        intent.setClass(this, OthersHomepageActivity.class);
        startActivity(intent);
    }

    //返回
    public void back(View view) {
        finish();
    }

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

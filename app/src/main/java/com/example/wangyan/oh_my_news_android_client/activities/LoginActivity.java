package com.example.wangyan.oh_my_news_android_client.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.Bean.User;
import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.okhttp.CommonOkHttpClient;
import com.example.wangyan.oh_my_news_android_client.okhttp.exception.OkHttpException;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataHandle;
import com.example.wangyan.oh_my_news_android_client.okhttp.listener.ResponseDataListener;
import com.example.wangyan.oh_my_news_android_client.okhttp.request.CommonRequest;
import com.example.wangyan.oh_my_news_android_client.services.LoginService;
import com.example.wangyan.oh_my_news_android_client.util.AutoLogin;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.DialogUtil;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.ExitApplication;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.JsonToObject;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.LoginConnection;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.Topbar;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Intent intent = new Intent();
    private Topbar topbar_login;
    private EditText et_loginUsername;
    private EditText et_loginPwd;
    private TextView tv_loginToRegist;
    private Button loginButton;
    private Handler handler;

    private LoginConnection conn;
    private LoginService loginService;
    private String username;
    private String pwd;
    private boolean isExit = false;

//    private int userId;
//    private boolean isLoginSuccess;
    private String type;
    private int articleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ExitApplication.getInstance().addActivity(this);

        initView();
        intent = getIntent();
        username = intent.getStringExtra("username");
        pwd = intent.getStringExtra("pwd");
        et_loginUsername.setText(username);
        et_loginPwd.setText(pwd);

        type = intent.getStringExtra("type");
        articleId = intent.getIntExtra("articleId",-1);
        Log.i("wangyan","..........."+type+"........"+articleId);


    }

    /**
     * 视图初始化
     */
    private void initView(){
        topbar_login = (Topbar)findViewById(R.id.top_login);
        et_loginUsername = (EditText)findViewById(R.id.et_loginUsername);
        et_loginPwd = (EditText)findViewById(R.id.et_loginPwd);
        tv_loginToRegist = (TextView)findViewById(R.id.tv_loginToRegist);
        loginButton = (Button)findViewById(R.id.loginButton);
//       topbar_login的监听事件
        topbar_login.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                exit(builder);
//                DialogUtil.showDialog(UserLoginActivity.this,"用户名或密码错误，请重新输入！",false);
            }

            @Override
            public void rightClick() {
                intent.setClass(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        转去注册的监听事件
        tv_loginToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        登陆按钮的监听事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                输入验证
                if (validate()){
//                    登陆的网络请求
                    login();
                }

            }
        });
        //登陆判断显示框
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Integer userId = msg.getData().getInt("userId");
                boolean isLoginSuccess = msg.getData().getBoolean("isLoginSuccess");
                String error = msg.getData().getString("error");
                if (isLoginSuccess){
                    AutoLogin autoLogin = new AutoLogin();
                    autoLogin.writeToFile(userId,isLoginSuccess);

                    ExitApplication.getInstance().userId = userId;
                    ExitApplication.getInstance().isLoginSuccess = isLoginSuccess;
                    intent.putExtra("userId",ExitApplication.getInstance().userId);
                    intent.putExtra("isLoginSuccess",ExitApplication.getInstance().isLoginSuccess);

                    Log.i("wangyan",ExitApplication.getInstance().userId+"...."+ExitApplication.getInstance().isLoginSuccess);
                      if ("mainPage".equals(type)) {
                          intent.putExtra("type",type);
                          intent.setClass(LoginActivity.this, MypageActivity.class);
                      }else if ("detail".equals(type)){
                          intent.putExtra("articleId",articleId);
                            intent.setClass(LoginActivity.this, DetailActivity.class);
                        }else if ("privateMsg".equals(type)){
                          intent.putExtra("type",type);
                            intent.setClass(LoginActivity.this,MypageActivity.class);
                        }else if ("homePage".equals(type)){
                          intent.putExtra("type",type);
                            intent.setClass(LoginActivity.this,MypageActivity.class);
                        }else {
                          intent.putExtra("type",type);
                          intent.setClass(LoginActivity.this, MypageActivity.class);
                      }
                    startActivity(intent);
                    finish();
                }else if (!isLoginSuccess){
                    Log.i("yan",userId+"...////..."+isLoginSuccess);
                    DialogUtil.showDialog(LoginActivity.this,"用户名或密码错误，请重新输入！",false);
                }else if("error".equals(error)){
                    DialogUtil.showDialog(LoginActivity.this,"网络响应异常，请稍后再试！",false);
                }

            }
        };
    }
    //    对输入的用户名和密码进行校验
    private boolean validate(){
        username = et_loginUsername.getText().toString().trim();
        if (username.equals("")){
            DialogUtil.showDialog(this,"用户名不能为空！",false);
            return false;
        }
        pwd = et_loginPwd.getText().toString().trim();
        if (pwd.equals("")){
            DialogUtil.showDialog(this,"密码不能为空！",false);
            return false;
        }
        return true;
    }
    //    登陆的网络请求
    private void login(){
//        进行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                responseData();
            }
        }).start();
    }
    private void responseData(){
        User params = new User();
            Log.i("yan",username+"..........wangyan..........."+pwd);
            params.setUsername(username);
            params.setPassword(pwd);
            String url = "/login/submitInfo";
            CommonOkHttpClient.post(CommonRequest.createPostResquest(url,params),new ResponseDataHandle(new ResponseDataListener() {
                Message msg = new Message();
                @Override
                public void onSuccess(Object responseObj) {

                    Map<String,Object> map = new HashMap<String, Object>();
                    map = JsonToObject.getLogin(responseObj);
                    Integer userId = (Integer)map.get("userId");
                    boolean isLoginSuccess = (boolean)map.get("isLoginSuccess");

                    Log.i("wangyan",userId+"......"+isLoginSuccess+"............");

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isLoginSuccess",isLoginSuccess);
                    bundle.putInt("userId",userId);
                    msg = new Message();
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }
                @Override
                public void onFailure(Object responseObj) {
                    Map<String,Object> map = new HashMap<String, Object>();
                    OkHttpException exception = (OkHttpException) responseObj;
                    if (exception.getEcode() == -1 && exception.getEmsg() == null){
                        Bundle bundle = new Bundle();
                        bundle.putString("error","error");
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                }
            }));
        }
    public void exit(AlertDialog.Builder builder){

        builder.setMessage("确定要退出登陆吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                LoginActivity.this.finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //创建并显示弹出的对话框
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

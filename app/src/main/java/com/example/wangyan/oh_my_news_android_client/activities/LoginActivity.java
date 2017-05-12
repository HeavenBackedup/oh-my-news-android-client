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

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.services.LoginService;
import com.example.wangyan.oh_my_news_android_client.util.AutoLogin;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.DialogUtil;
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
    private String detail;
    private String privateMsg;
    private String homePage;
    private String mainPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        intent = getIntent();
        username = intent.getStringExtra("username");
        pwd = intent.getStringExtra("pwd");
        detail = intent.getStringExtra("detail");
        privateMsg = intent.getStringExtra("privateMsg");
        homePage = intent.getStringExtra("homePage");
        mainPage = intent.getStringExtra("mainPage");
        et_loginUsername.setText(username);
        et_loginPwd.setText(pwd);
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
                    Log.i("yan",userId+"...////..."+isLoginSuccess);
                    intent.putExtra("userId",userId);
                    intent.putExtra("isLoginSuccess",isLoginSuccess);
                      if ("mainPage".equals(mainPage)) {
                          intent.setClass(LoginActivity.this, MainpageActivity.class);
                      }else if ("detail".equals(detail)){
                            intent.setClass(LoginActivity.this, DetailActivity.class);
                        }else if ("privateMsg".equals(privateMsg)){
                            intent.setClass(LoginActivity.this,PrivateMsgActivity.class);
                        }else if ("homePage".equals(homePage)){
                            intent.setClass(LoginActivity.this,HomepageActivity.class);
                        }
                    intent.setClass(LoginActivity.this, MainpageActivity.class);
                    startActivity(intent);
                    finish();
                }else if (!isLoginSuccess){
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
//        传值给loginService
        intent.putExtra("username",username);
        intent.putExtra("pwd",pwd);
        intent.setClass(this, LoginService.class);
        conn = new LoginConnection();
        bindService(intent,conn,BIND_AUTO_CREATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isExit) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loginService = conn.getLoginService();
                    if (loginService != null) {
                        isExit = true;
                        loginService.setCallback(new LoginService.Callback() {
                            @Override
                            public void onDataChange(Object data) {

                                Map<String,Object> map = new HashMap<String, Object>();
                                map = (Map<String,Object>)data;
                                Integer userId = (Integer)map.get("userId");
                                boolean isLoginSuccess = (boolean)map.get("isLoginSuccess");
                                String error = (String)map.get("error");

                                Log.i("wangyan",userId+"......"+isLoginSuccess+"............"+error);

                                Bundle bundle = new Bundle();
                                bundle.putBoolean("isLoginSuccess",isLoginSuccess);
                                bundle.putInt("userId",userId);
                                bundle.putString("error",error);
                                Message msg = new Message();
                                msg.setData(bundle);
                                handler.sendMessage(msg);

                            }
                        });
                    }
                }
            }
        }).start();
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
        if (conn!= null) {
        unbindService(conn);
        }

    }
}

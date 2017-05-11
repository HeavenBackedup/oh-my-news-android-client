package com.example.wangyan.oh_my_news_android_client.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wangyan.oh_my_news_android_client.R;
import com.example.wangyan.oh_my_news_android_client.services.LoginService;
import com.example.wangyan.oh_my_news_android_client.services.RegistService;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.DialogUtil;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.RegistConnection;
import com.example.wangyan.oh_my_news_android_client.util.MainPage.Topbar;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistActivity extends AppCompatActivity {
    private Intent intent = new Intent();
    private Topbar topbar_regist;
    private EditText et_registUsername;
    private EditText et_registPwd;
    private EditText et_registPwdAgain;
    private TextView tv_registTologin;
    private Button registButton;
    private Handler handlerSubmit;

    private String username;
    private String pwd;
    private String rePwd;
    private RegistConnection conn;
    private RegistService registService;
    //判断数据是否可提交，isRegistExist为true时可提交
    private boolean isSubmit = false;
    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }
    private void initView(){
        topbar_regist = (Topbar)findViewById(R.id.topbar_regist);
        et_registUsername = (EditText)findViewById(R.id.et_registUsername);
        et_registPwd= (EditText)findViewById(R.id.et_registPwd);
        et_registPwdAgain = (EditText)findViewById(R.id.et_registPwdAgain);
        tv_registTologin = (TextView)findViewById(R.id.tv_registToLogin);
        registButton = (Button)findViewById(R.id.registButton);
//       topbar_regist的监听事件
        topbar_regist.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                exit(builder);
            }

            @Override
            public void rightClick() {
                intent.setClass(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //        转去登录的监听事件
        tv_registTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                注册验证
                if (validate()){
                    //   注册请求
                       regist();
                }
            }
        });
        handlerSubmit = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isSubmit = (Boolean)msg.obj;
                if (isSubmit){
//                    DialogUtil.showDialog(RegistActivity.this,"注册成功!",false);
                    intent.putExtra("username",username);
                    intent.putExtra("pwd",pwd);
                    intent.setClass(RegistActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    et_registUsername.setText("");
                }
            }
        };
    }
    private boolean validate(){
        username = et_registUsername.getText().toString().trim();
        if (username.equals("")) {
            DialogUtil.showDialog(this,"用户名不能为空！",false);
            return false;
        }else if (!(username.matches("^\\w+"))) {
                DialogUtil.showDialog(this, "用户名只能包含字母、数字和下划线！", false);
                return false;
            }
        pwd = et_registPwd.getText().toString().trim();
        if (pwd.equals("")) {
            DialogUtil.showDialog(this, "密码不能为空！", false);
            return false;
        }else if (pwd.length()<6){
            DialogUtil.showDialog(this,"密码至少六位！",false);
            return false;
        }
        rePwd = et_registPwdAgain.getText().toString().trim();
        if (rePwd.equals("")) {
            DialogUtil.showDialog(this, "请确认密码！", false);
            return false;
        }else if (!(rePwd.equals(pwd))){
            DialogUtil.showDialog(this,"两次密码输入不同！",false);
            return false;
        }
        return true;
    }
    private void regist(){
        intent.putExtra("username",username);
        intent.putExtra("pwd",pwd);
        intent.setClass(this,RegistService.class);

        conn = new RegistConnection();
        bindService(intent,conn,BIND_AUTO_CREATE);
        Log.i("yan",username+".................123wwwwwwww445..............."+pwd);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isExit) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    registService = conn.getRegistService();
                    if (registService != null) {
                        isExit = true;
                        registService.setCallback(new RegistService.Callback() {
                            @Override
                            public void onDataChange(Object data) {
                                Log.i("yan", data.toString() + "mainactivitty............");
                                Message msg = new Message();
                                msg.obj = data;
                                handlerSubmit.sendMessage(msg);
                            }
                        });
                    }
                }
            }
        }).start();
    }

//    topbar左侧按钮退出函数
    public void exit(AlertDialog.Builder builder){
        builder.setMessage("确定要退出注册吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               RegistActivity.this.finish();
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
        if (conn != null){
            unbindService(conn);
        }
        isExit = true;
    }
}

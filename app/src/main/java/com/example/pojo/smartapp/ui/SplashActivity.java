package com.example.pojo.smartapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pojo.smartapp.MainActivity;
import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.utils.ShareUtils;
import com.example.pojo.smartapp.utils.StaticClass;
import com.example.pojo.smartapp.utils.UtilTools;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/11 10:06
 * 描述:  闪屏页
 */

public class SplashActivity  extends AppCompatActivity {

    private TextView tv_splash;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                   if (isFirst()){
                       startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                   }else{
                       String username = ShareUtils.getString(SplashActivity.this, "username", null);
                       if (username != null){
                           startActivity(new Intent(SplashActivity.this, MainActivity.class));
                       }else {
                           startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                       }
                   }
                   finish();

                   break;
            }
        }
    };


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        //发送handler
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,1000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);
        UtilTools.setFonts(this,tv_splash);

    }
    //判断是否是第一次登陆
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if (isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return  false;
        }

    }
    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}

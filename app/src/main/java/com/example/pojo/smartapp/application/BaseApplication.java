package com.example.pojo.smartapp.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.example.pojo.smartapp.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.application
 * 创建者:  zsj
 * 创建事件: 2017/4/10 15:09
 * 描述:
 */

public class BaseApplication extends Application {


    //创建app初始化
    @Override
    public void onCreate() {
        super.onCreate();

        //Bugly 初始化
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);
        //Bmob  初始化
        Bmob.initialize(getApplicationContext(), StaticClass.BMOB_APPID);
        //科大讯飞
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.IFLYTEK);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

    }

}



package com.example.pojo.smartapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/10 15:13
 * 描述:  activity 基类
 */

/**
 * 主要做的事情
 * 1. 统一的属性
 * 2.统一的接口
 * 3.统一的方法
 *
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    //菜单栏操作


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

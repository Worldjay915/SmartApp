package com.example.pojo.smartapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.pojo.smartapp.fragment.GirlFragment;
import com.example.pojo.smartapp.fragment.SmartFragment;
import com.example.pojo.smartapp.fragment.UserFragment;
import com.example.pojo.smartapp.fragment.WechatFragment;
import com.example.pojo.smartapp.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Tablayout
    private  TabLayout  mTabLayout;
    //ViewPager
    private ViewPager  mViewPager;
    //title
    private List<String> mTitle;
    //fragment
    private List<Fragment> mFragment;
    //悬浮Button
    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
      //  CrashReport.testJavaCrash();

    }

    //初始化数据
    public void initData(){
        mTitle = new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new SmartFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());

    }

    public void initView(){
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        fab_setting.setVisibility(View.GONE);

        //viewpager 滑动监听
         mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                  if (position == 0){
                      fab_setting.setVisibility(View.GONE);
                  }else {
                      fab_setting.setVisibility(View.VISIBLE);
                  }

             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });


        //viewPager 预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
            //设置tablayout的标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //tablayout 绑定 viewpager
        mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
        }

    }
}

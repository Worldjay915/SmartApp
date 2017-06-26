package com.example.pojo.smartapp.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/11 16:17
 * 描述:
 */

public class RegisterActivity  extends  BaseActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_password1;
    private EditText et_password2;
    private EditText et_email;
    private Button btnRegisted;
    private boolean isGender = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        et_email = (EditText) findViewById(R.id.et_email);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        btnRegisted = (Button) findViewById(R.id.btnRegisted);
        btnRegisted.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRegisted:
                String username = et_user.getText().toString().trim();
                String   age     = et_age.getText().toString().trim();
                String  desc    = et_desc.getText().toString().trim();
                String password1= et_password1.getText().toString().trim();
                String password2= et_password2.getText().toString().trim();
                String email    =et_email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(username)&!TextUtils.isEmpty(age)
                        &!TextUtils.isEmpty(password1)
                        &!TextUtils.isEmpty(password2)&!TextUtils.isEmpty(email)){

                    //判断两次输入密码是否一致
                    if (password1.equals(password2)){

                        //判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                                if (checkedId==R.id.rb_boy){
                                    isGender = true;
                                }else if (checkedId==R.id.rb_girl){
                                    isGender = false;
                                }

                            }
                        });

                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc ="这个人比较懒，什么都没有写...";
                        }

                        //注册
                        User user = new User();
                        user.setUsername(username);
                        user.setAge(Integer.parseInt(age));
                        user.setDesc(desc);
                        user.setSex(isGender);
                        user.setEmail(email);
                        user.setPassword(password2);

                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e == null){
                                    Toast.makeText(RegisterActivity.this, "您已注册成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this, "注册失败"+e, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }else {

                        Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }


                }else {

                    Toast.makeText(RegisterActivity.this, "输入框不能为空！！", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
}

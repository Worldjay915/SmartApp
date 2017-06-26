package com.example.pojo.smartapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/11 21:43
 * 描述:  修改密码页面
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_old_password;
    private EditText et_new_password1;
    private EditText et_new_password2;
    private EditText et_email;
    private Button   btn_update_pasword;
    private Button   btn_set_email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();

    }

    private void initView() {
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password1 = (EditText) findViewById(R.id.et_new_password1);
        et_new_password2 = (EditText) findViewById(R.id.et_new_password2);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_update_pasword = (Button) findViewById(R.id.btn_update_password);
        btn_set_email = (Button) findViewById(R.id.btn_set_email);
        btn_update_pasword.setOnClickListener(this);
        btn_set_email.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_update_password:

                String oldpassword = et_old_password.getText().toString().trim();
                String newpasword1 = et_new_password1.getText().toString().trim();
                String newpssword2 = et_new_password2.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(oldpassword)&!TextUtils.isEmpty(newpasword1)&
                        !TextUtils.isEmpty(newpssword2)){
                    //判断两次密码输入是否相等
                    if (newpasword1.equals(newpssword2)){
                       //重置密码
                        User.updateCurrentUserPassword(oldpassword, newpssword2, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                               if (e ==null){
                                   Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                   finish();
                               }else{

                                   Toast.makeText(ForgetPasswordActivity.this, "重置密码失败"+e.toString(), Toast.LENGTH_SHORT).show();
                               }
                            }
                        });


                    }else {
                        Toast.makeText(this, "两次输入不一致", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case  R.id.btn_set_email:
               String  email = et_email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(email)){

                    User.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                          if (e==null){
                              Toast.makeText(ForgetPasswordActivity.this, "邮件发送成功，请注意查收", Toast.LENGTH_SHORT).show();
                              finish();
                          }else {
                              Toast.makeText(ForgetPasswordActivity.this, "邮件发送失败"+e.toString(), Toast.LENGTH_SHORT).show();

                          }
                        }
                    });

                }else {
                    Toast.makeText(this, "输入不能输入为空", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
}

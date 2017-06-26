package com.example.pojo.smartapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.smartapp.MainActivity;
import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.User;
import com.example.pojo.smartapp.utils.ShareUtils;
import com.example.pojo.smartapp.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/11 16:01
 * 描述:
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register;
    private EditText et_user;
    private EditText et_password;
    private Button  btn_login;
    private CheckBox save_pass;
    private TextView tv_forget_password;
    private CustomDialog dialog ;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        save_pass = (CheckBox) findViewById(R.id.save_pasword);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        tv_forget_password.setOnClickListener(this);
        //对话框
        dialog = new CustomDialog(this,150,150,R.layout.dialog_loding,R.style.Theme_dialog, Gravity.CENTER);

        //一开始登录的初始状态
        boolean isSave = ShareUtils.getBoolean(this,"savepassword",false);
        save_pass.setChecked(isSave);

        //之后登录进行判断
        if (isSave){
            String name = ShareUtils.getString(this,"username","");
            String password = ShareUtils.getString(this,"password","");
            et_user.setText(name);
            et_password.setText(password);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
               break;
            case  R.id.btn_login:
                String name = et_user.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //判断是否输入位空
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){
                    //登录验证
                    dialog.show();
                    User user = new User();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                //验证邮箱是否验证
                                dialog.dismiss();
                                if(user.getEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }else {
                                    Toast.makeText(LoginActivity.this,"用户邮箱没有验证，请在您邮箱进行验证",Toast.LENGTH_SHORT).show();
                                }
                               // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }else{
                    Toast.makeText(this, "用户名或密码输入为空！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //默认保存用户名和密码
        ShareUtils.putBoolean(this,"savepassword",save_pass.isChecked());
        if (save_pass.isChecked()){
            ShareUtils.putString(this,"username",et_user.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }else {
            ShareUtils.deleteOne(this,"username");
            ShareUtils.deleteOne(this,"password");
        }

    }
}

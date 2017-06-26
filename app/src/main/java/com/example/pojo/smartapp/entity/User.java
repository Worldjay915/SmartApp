package com.example.pojo.smartapp.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.entity
 * 创建者:  zsj
 * 创建事件: 2017/4/11 17:08
 * 描述:
 */

public class User extends BmobUser {

    private Integer age;
    private boolean sex;
    private String  desc;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

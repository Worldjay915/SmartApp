package com.example.pojo.smartapp.entity;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.entity
 * 创建者:  zsj
 * 创建事件: 2017/4/14 11:16
 * 描述:
 */

public class WechatData {

    private String imgUrl;
    private String title;
    private String source;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： AdvertisementBean
 * @classDescription：
 * @author： 万
 * @createTime： 2017/11/11 11:40
 */
public class AdvertisementBean implements Serializable, LotteryType {
    private String title; // 标题
    private String date;  // 时间
    private String content; // 文字内容
    private String jump_url; // 图片
    private String is_picture; // 判断显示图片或者文字


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getIs_picture() {
        return is_picture;
    }

    public void setIs_picture(String is_picture) {
        this.is_picture = is_picture;
    }
}

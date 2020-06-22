package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： MainIndexExtensionInfoBean
 * @classDescription： 首页推广任务
 * @author： 万
 * @createTime： 2017/12/14 18:48
 */
public class MainIndexExtensionInfoBean implements Serializable{
    private String title;
    private String des;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}

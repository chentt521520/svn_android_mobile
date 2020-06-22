package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： MyFriendBean
 * @classDescription： 我的好友列表实体类
 * @author： 万
 * @createTime： 2017/11/11 14:25
 */
public class MyFriendBean implements Serializable {
    private String id; // 好友ID
    private String name; // 好友昵称
    private String img; // 头像
    private String createTime; // 创建时间
    private String money; // 奖励金额
    private String phone; // 手机号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

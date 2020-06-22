package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： AccountDetailsBean
 * @classDescription：
 * @author： 万
 * @createTime： 2018/1/31 18:33
 */
public class AccountDetailsBean implements Serializable {
    private String id;
    private String money;
    private String status;
    private String time;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

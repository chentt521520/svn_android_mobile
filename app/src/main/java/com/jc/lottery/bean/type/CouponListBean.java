package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： TicketLocationListBean
 * @classDescription： 优惠券列表实体类
 * @author： 万
 * @createTime： 2017/11/9 16:09
 */
public class CouponListBean implements Serializable {
    private String type; // 优惠券类型（满减券）
    private String mMoney; // 满多少
    private String jMoney; // 减多少
    private String date; // 时间

    public CouponListBean() {
    }

    public CouponListBean(String type, String mMoney, String jMoney, String date) {
        this.type = type;
        this.mMoney = mMoney;
        this.jMoney = jMoney;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getmMoney() {
        return mMoney;
    }

    public void setmMoney(String mMoney) {
        this.mMoney = mMoney;
    }

    public String getjMoney() {
        return jMoney;
    }

    public void setjMoney(String jMoney) {
        this.jMoney = jMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

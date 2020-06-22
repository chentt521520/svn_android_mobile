package com.jc.lottery.bean.resp;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:26
 */

public class DrawListBean {
    private String draw;
    private String id;
    private String income;
    private String payMoney;
    private String prizeNum;
    private String prizeNumOrder;
    private long prizeTime;

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeNum(String prizeNum) {
        this.prizeNum = prizeNum;
    }

    public String getPrizeNumOrder() {
        return prizeNumOrder;
    }

    public void setPrizeNumOrder(String prizeNumOrder) {
        this.prizeNumOrder = prizeNumOrder;
    }

    public long getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(long prizeTime) {
        this.prizeTime = prizeTime;
    }
}

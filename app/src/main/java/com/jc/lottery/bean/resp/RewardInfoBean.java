package com.jc.lottery.bean.resp;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/7/25.
 */

//兑奖记录
public class RewardInfoBean implements Serializable {

    private String gameName;
    private String cashState;
    private String cashMoney;
    private String cashTime;
    private String orderCode;
    private String safetyCode;
    private String channel;
    private String bookNum;
    private String ticketNum;
    private int index;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCashState() {
        return cashState;
    }

    public void setCashState(String cashState) {
        this.cashState = cashState;
    }

    public String getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(String cashMoney) {
        this.cashMoney = cashMoney;
    }

    public String getCashTime() {
        return cashTime;
    }

    public void setCashTime(String cashTime) {
        this.cashTime = cashTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSafetyCode() {
        return safetyCode;
    }

    public void setSafetyCode(String safetyCode) {
        this.safetyCode = safetyCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

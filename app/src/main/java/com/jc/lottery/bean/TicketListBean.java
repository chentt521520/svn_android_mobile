package com.jc.lottery.bean;

import java.io.Serializable;

/**
 * @ Create_time: 2018/9/19 on 11:19.
 * @ description：票列表
 * @ author: vchao
 */
public class TicketListBean implements Serializable{
    /**
     * ticket : 10
     * eachTotalMoney : 200
     * eachBetMode : 01
     */

    private String ticket;
    private String eachTotalMoney;
    private String eachBetMode;

    public TicketListBean(String ticket, String eachTotalMoney, String eachBetMode) {
        this.ticket = ticket;
        this.eachTotalMoney = eachTotalMoney;
        this.eachBetMode = eachBetMode;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getEachTotalMoney() {
        return eachTotalMoney;
    }

    public void setEachTotalMoney(String eachTotalMoney) {
        this.eachTotalMoney = eachTotalMoney;
    }

    public String getEachBetMode() {
        return eachBetMode;
    }

    public void setEachBetMode(String eachBetMode) {
        this.eachBetMode = eachBetMode;
    }
}
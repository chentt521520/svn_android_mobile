package com.jc.lottery.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/7/25.
 */
//领取记录
public class RecordInfoBean implements Serializable {

    private String gameName;
    private String ticketNum;
    private String getTime;
    private String recipient;
    private String getDevice;
    private String sendDevice;
    private String sendPerson;
    private String payState;
    private String orderCode;
    private String status;
    private int id;
    private String createTime;
    private String getChannels;
    private String payMoney;
    private String shutState;
    private int index;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getGetDevice() {
        return getDevice;
    }

    public void setGetDevice(String getDevice) {
        this.getDevice = getDevice;
    }

    public String getSendDevice() {
        return sendDevice;
    }

    public void setSendDevice(String sendDevice) {
        this.sendDevice = sendDevice;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGetChannels() {
        return getChannels;
    }

    public void setGetChannels(String getChannels) {
        this.getChannels = getChannels;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getShutState() {
        return shutState;
    }

    public void setShutState(String shutState) {
        this.shutState = shutState;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

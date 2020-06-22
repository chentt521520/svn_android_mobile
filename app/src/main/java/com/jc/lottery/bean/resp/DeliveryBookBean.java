package com.jc.lottery.bean.resp;

import java.io.Serializable;

/**
 * @author lr
 * @description:
 * @date:${DATA} 10:20
 */

public class DeliveryBookBean implements Serializable,Cloneable{

    private String activeState;
    private String bookNum;
    private String deliveryId;
    private String logisticsCode;
    private String schemeNum;
    private String settleStatus;
    private String sheetsNum;
    private String ticketNo;
    private String bookId;
    private String cartonNo;
    private String cartonNoId;
    private boolean type = true;
    private boolean bookType = false;
    private String cartonType;
    private String titleNot = "";



    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getSchemeNum() {
        return schemeNum;
    }

    public void setSchemeNum(String schemeNum) {
        this.schemeNum = schemeNum;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    public String getSheetsNum() {
        return sheetsNum;
    }

    public void setSheetsNum(String sheetsNum) {
        this.sheetsNum = sheetsNum;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getCartonNoId() {
        return cartonNoId;
    }

    public void setCartonNoId(String cartonNoId) {
        this.cartonNoId = cartonNoId;
    }

    public String getCartonType() {
        return cartonType;
    }

    public void setCartonType(String cartonType) {
        this.cartonType = cartonType;
    }

    public String getTitleNot() {
        return titleNot;
    }

    public void setTitleNot(String titleNot) {
        this.titleNot = titleNot;
    }

    public boolean isBookType() {
        return bookType;
    }

    public void setBookType(boolean bookType) {
        this.bookType = bookType;
    }

    //实现这个方法
    public DeliveryBookBean clone() throws CloneNotSupportedException {
        return (DeliveryBookBean)super.clone();
    }
}

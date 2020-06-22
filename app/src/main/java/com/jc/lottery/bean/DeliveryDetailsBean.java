package com.jc.lottery.bean;

/**
 * @author lr
 * @description:
 * @date:${DATA} 10:56
 */

public class DeliveryDetailsBean {

    private String activeState;
    private String activeTime;
    private String bookNum;
    private String schemeNum;

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getSchemeNum() {
        return schemeNum;
    }

    public void setSchemeNum(String schemeNum) {
        this.schemeNum = schemeNum;
    }
}

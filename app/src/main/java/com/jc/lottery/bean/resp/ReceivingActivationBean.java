package com.jc.lottery.bean.resp;

/**
 * Created by Administrator on 2019/8/14.
 */

public class ReceivingActivationBean {

    private String activeState;
    private String bookNum;
    private String endTicketNum;
    private String schemeNum;
    private String startTicketNum;
    private String activeTime;
    private int index;

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

    public String getEndTicketNum() {
        return endTicketNum;
    }

    public void setEndTicketNum(String endTicketNum) {
        this.endTicketNum = endTicketNum;
    }

    public String getSchemeNum() {
        return schemeNum;
    }

    public void setSchemeNum(String schemeNum) {
        this.schemeNum = schemeNum;
    }

    public String getStartTicketNum() {
        return startTicketNum;
    }

    public void setStartTicketNum(String startTicketNum) {
        this.startTicketNum = startTicketNum;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

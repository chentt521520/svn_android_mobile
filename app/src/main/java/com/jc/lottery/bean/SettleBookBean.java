package com.jc.lottery.bean;

import java.io.Serializable;

/**
 * @author lr
 * @description:
 * @date:${DATA} 16:30
 */

public class SettleBookBean implements Serializable {

    private String bookNum;
    private String schemeNum;
    private String settleStatus;
    private boolean type = false;

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

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}

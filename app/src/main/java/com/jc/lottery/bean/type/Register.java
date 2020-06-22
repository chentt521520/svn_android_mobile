package com.jc.lottery.bean.type;

import java.io.Serializable;

public class Register implements LotteryType, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String status;
    private int intStatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

}

package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * Created by zhengchengpeng on 2017/8/31.
 */

public class PayInfo implements LotteryType, Serializable {

    private String status;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

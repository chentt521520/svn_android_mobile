package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * Created by zhengchengpeng on 2017/8/25.
 */

public class SendAuthCodeType implements LotteryType, Serializable {

    private String status;
    private String msg;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

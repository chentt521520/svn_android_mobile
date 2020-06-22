package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： DeleteOrderBean
 * @classDescription： 删除订单
 * @author： 万
 * @createTime： 2017/12/11 16:01
 */
public class DeleteOrderBean implements Serializable, LotteryType {
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

package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： AliPayBean
 * @classDescription： 支付宝支付实体类
 * @author： 万
 * @createTime： 2017/11/26 12:44
 */
public class AliPayBean implements Serializable, LotteryType {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.jc.lottery.bean;

/**
 * @author lr
 * @description:
 * @date:${DATA} 15:50
 */

public class WalletListBean {

    private String channel;
    private String createTime;
    private String money;
    private String remark;
    private String type;
    private String walleType;
    private int index;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWalleType() {
        return walleType;
    }

    public void setWalleType(String walleType) {
        this.walleType = walleType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

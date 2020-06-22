package com.jc.lottery.bean;

/**
 * Created by Administrator on 2019/8/28.
 */

public class VictorySettlementRecordBean {

    private String channels;
    private String order_code;
    private String settle_condition;
    private String total_money;
    private String money_status;
    private String settle_status;
    private String create_time;
    private String user_name;
    private String user_role;
    private String auditor;
    private String audit_time;
    private int index;

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getSettle_condition() {
        return settle_condition;
    }

    public void setSettle_condition(String settle_condition) {
        this.settle_condition = settle_condition;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getMoney_status() {
        return money_status;
    }

    public void setMoney_status(String money_status) {
        this.money_status = money_status;
    }

    public String getSettle_status() {
        return settle_status;
    }

    public void setSettle_status(String settle_status) {
        this.settle_status = settle_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(String audit_time) {
        this.audit_time = audit_time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

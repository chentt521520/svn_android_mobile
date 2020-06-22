package com.jc.lottery.bean.resp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 16:59
 */

public class PaymentBean {

    private String message;
    private String pageCount;
    private String pageNum;
    private String state;
    private String totalNum;
    private List<PayListBean> payList = new ArrayList<PayListBean>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public List<PayListBean> getPayList() {
        return payList;
    }

    public void setPayList(List<PayListBean> payList) {
        this.payList = payList;
    }

    public class PayListBean{
        private String orderCode;
        private String payMethod;
        private String payMoney;
        private String payState;
        private Long payTime;
        private String phone;
        private String rechargeAmount;
        private int index;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(String payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public Long getPayTime() {
            return payTime;
        }

        public void setPayTime(Long payTime) {
            this.payTime = payTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}

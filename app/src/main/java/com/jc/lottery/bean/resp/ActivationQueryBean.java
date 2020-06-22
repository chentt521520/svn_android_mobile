package com.jc.lottery.bean.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description: 激活查询
 * @date:${DATA} 9:32
 */

public class ActivationQueryBean implements Serializable{

    private String orderCode;
    private String ticketNum;
    private String createTime;
    private String deliveryId;
    private boolean type;
    private boolean openType;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isOpenType() {
        return openType;
    }

    public void setOpenType(boolean openType) {
        this.openType = openType;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public class BookInfo implements Serializable{
        private String bookId;
        private String bookNum;
        private String deliveryId;
        private String orderCode;

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookNum() {
            return bookNum;
        }

        public void setBookNum(String bookNum) {
            this.bookNum = bookNum;
        }

        public String getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }
    }
}

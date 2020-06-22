package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class pos_GetLogisticsDelivery {
    /**
     * interfaceCode : winQuery
     * requestTime : 1455606858
     * accountName : ceshi
     * password : ceshi
     * channel : ceshi
     * data : {"rechargeCardInfo":{"safetyCode": "13CE59C2-2CDC19A1-EA5EFD4D-22303756"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private DataBean data;

    public pos_GetLogisticsDelivery(String accountName, String password , String channel, DataBean data) {
        this.interfaceCode = "delivery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = data;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * rechargeCardInfo : {"gameAlias": "jkc"}
         */
        private DeliveryInfo deliveryInfo;

        public DataBean(DeliveryInfo deliveryInfo) {
            this.deliveryInfo = deliveryInfo;
        }

        public DeliveryInfo getDeliveryInfo() {
            return deliveryInfo;
        }

        public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
            this.deliveryInfo = deliveryInfo;
        }
    }

    public static class DeliveryInfo{
        private String gameAlias;
        private String ticketNum;
        private String sendDeviceId;
        private String sendDevice;
        private String sendPersonId;
        private String sendPerson;
        private String orderCode;
        private List<BookNoListInfo> bookNoList = new ArrayList<BookNoListInfo>();

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getTicketNum() {
            return ticketNum;
        }

        public void setTicketNum(String ticketNum) {
            this.ticketNum = ticketNum;
        }

        public String getSendDeviceId() {
            return sendDeviceId;
        }

        public void setSendDeviceId(String sendDeviceId) {
            this.sendDeviceId = sendDeviceId;
        }

        public String getSendDevice() {
            return sendDevice;
        }

        public void setSendDevice(String sendDevice) {
            this.sendDevice = sendDevice;
        }

        public String getSendPersonId() {
            return sendPersonId;
        }

        public void setSendPersonId(String sendPersonId) {
            this.sendPersonId = sendPersonId;
        }

        public String getSendPerson() {
            return sendPerson;
        }

        public void setSendPerson(String sendPerson) {
            this.sendPerson = sendPerson;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public List<BookNoListInfo> getBookNoList() {
            return bookNoList;
        }

        public void setBookNoList(List<BookNoListInfo> bookNoList) {
            this.bookNoList = bookNoList;
        }
    }

    public static class BookNoListInfo{
        private String bookNo;
        private String ticketNo;
        private String logisticsCode;
        private String schemeNum;
        private String bookId;
        private String cartonNo;
        private String cartonNoId;
        private String sheetsNum;

        public String getBookNo() {
            return bookNo;
        }

        public void setBookNo(String bookNo) {
            this.bookNo = bookNo;
        }

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public String getLogisticsCode() {
            return logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getSchemeNum() {
            return schemeNum;
        }

        public void setSchemeNum(String schemeNum) {
            this.schemeNum = schemeNum;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getCartonNo() {
            return cartonNo;
        }

        public void setCartonNo(String cartonNo) {
            this.cartonNo = cartonNo;
        }

        public String getCartonNoId() {
            return cartonNoId;
        }

        public void setCartonNoId(String cartonNoId) {
            this.cartonNoId = cartonNoId;
        }

        public String getSheetsNum() {
            return sheetsNum;
        }

        public void setSheetsNum(String sheetsNum) {
            this.sheetsNum = sheetsNum;
        }
    }

}

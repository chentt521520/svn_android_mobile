package com.jc.lottery.bean.req;


import com.jc.lottery.bean.resp.ActivationQueryBean;
import com.jc.lottery.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：激活
 * @ author: lr
 */
public class pos_GetActivation {
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

    public pos_GetActivation(String accountName, String password , String channel, List<ActiveList> activeList) {
        this.interfaceCode = "activation";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(activeList);
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

        private List<ActiveList> activeList = new ArrayList<ActiveList>();

        public DataBean(List<ActiveList> activeList) {
            this.activeList = activeList;
        }

        public List<ActiveList> getActiveList() {
            return activeList;
        }

        public void setActiveList(List<ActiveList> activeList) {
            this.activeList = activeList;
        }
    }

    public static class ActiveList{
        private String deliveryId;

        public ActiveList(){

        }

        public ActiveList(String deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
        }
    }

    public static class BookInfo{
        private String bookId;

        public BookInfo(String bookId) {
            this.bookId = bookId;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }
    }
}

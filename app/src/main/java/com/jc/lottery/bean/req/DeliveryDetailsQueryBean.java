package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：激活详情查询
 * @ author: lr
 */
public class DeliveryDetailsQueryBean {
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

    public DeliveryDetailsQueryBean(String accountName, String password , String channel,DataBean data) {
        this.interfaceCode = "deliveryDetailsQuery";
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
        private DeliveryInfoBean deliveryInfo;

        public DataBean(DeliveryInfoBean deliveryInfo) {
            this.deliveryInfo = deliveryInfo;
        }
    }

    public static class DeliveryInfoBean {
        private String deliveryId;
        private String pageNo;

        public DeliveryInfoBean(String deliveryId, String pageNo) {
            this.deliveryId = deliveryId;
            this.pageNo = pageNo;
        }

        public String getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }
    }

}

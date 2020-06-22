package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class pos_GetManagement {
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
    private DataBean data;

    public pos_GetManagement(String accountName, String password , DataBean data) {
        this.interfaceCode = "managementQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
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
        private MangeData mangeData;

        public DataBean(MangeData mangeData) {
            this.mangeData = mangeData;
        }

        public MangeData getMangeData() {
            return mangeData;
        }

        public void setMangeData(MangeData mangeData) {
            this.mangeData = mangeData;
        }
    }

    public static class MangeData{
        private String deliveryId;
        private String deliveryOrder;

        public MangeData(String deliveryId, String deliveryOrder) {
            this.deliveryId = deliveryId;
            this.deliveryOrder = deliveryOrder;
        }

        public String getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getDeliveryOrder() {
            return deliveryOrder;
        }

        public void setDeliveryOrder(String deliveryOrder) {
            this.deliveryOrder = deliveryOrder;
        }
    }

}

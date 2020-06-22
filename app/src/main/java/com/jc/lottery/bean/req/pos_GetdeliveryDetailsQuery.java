package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：结算列表
 * @ author: lr
 */
public class pos_GetdeliveryDetailsQuery {
    /**
     * interfaceCode : rechargeCardQuery
     * requestTime : 1455606858
     * accountName : ceshi
     * password : ceshi
     * channel : ceshi
     * data : {"rechargeCardInfo":{"cardNumber":"10013300716553096874"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private DataBean data;

    public pos_GetdeliveryDetailsQuery(String accountName, String password , String channel, DataBean.DeliveryInfo deliveryInfo) {
        this.interfaceCode = "deliveryDetailsQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(deliveryInfo);
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

        public static class DeliveryInfo {

            private String deliveryId;
            private String pageNo;
            private String cartonNo;
            private String bookNum;

            public DeliveryInfo(String deliveryId, String pageNo, String cartonNo, String bookNum) {
                this.deliveryId = deliveryId;
                this.pageNo = pageNo;
                this.cartonNo = cartonNo;
                this.bookNum = bookNum;
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

            public String getCartonNo() {
                return cartonNo;
            }

            public void setCartonNo(String cartonNo) {
                this.cartonNo = cartonNo;
            }

            public String getBookNum() {
                return bookNum;
            }

            public void setBookNum(String bookNum) {
                this.bookNum = bookNum;
            }
        }
    }


}

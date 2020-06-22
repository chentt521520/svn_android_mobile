package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：兑奖
 * @ author: lr
 */
public class pos_CreateOrder {
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

    public pos_CreateOrder(String accountName, String password , String channel, DataBean.DeliveryInfo deliveryInfo) {
        this.interfaceCode = "createOrder";
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

        public DataBean(DeliveryInfo data) {
            this.deliveryInfo = data;
        }

        public DeliveryInfo getData() {
            return deliveryInfo;
        }

        public void setData(DeliveryInfo data) {
            this.deliveryInfo = data;
        }

        public static class DeliveryInfo{

            private String gameAlias;
            private String ticketNum;
//            private String recipientId;
//            private String recipient;
            private String getdeviceId;
            private String getdevice;
//            private String payState;
//            private String orderCode;

            public DeliveryInfo(){

            }

            public DeliveryInfo(String gameAlias, String ticketNum, String getdeviceId, String getdevice) {
                this.gameAlias = gameAlias;
                this.ticketNum = ticketNum;
                this.getdeviceId = getdeviceId;
                this.getdevice = getdevice;
            }

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

            public String getGetdeviceId() {
                return getdeviceId;
            }

            public void setGetdeviceId(String getdeviceId) {
                this.getdeviceId = getdeviceId;
            }

            public String getGetdevice() {
                return getdevice;
            }

            public void setGetdevice(String getdevice) {
                this.getdevice = getdevice;
            }
        }
    }
}

package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：兑奖
 * @ author: lr
 */
public class pos_WinQueryInfo {
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

    public pos_WinQueryInfo(String accountName, String password , String channel, DataBean.OrderInfo orderInfo) {
        this.interfaceCode = "winQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(orderInfo);
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

        private OrderInfo orderInfo;

        public DataBean(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class OrderInfo {

            private String securityCode;  //保安码
            private String manualCode;    //手工验证码
            private String logisticsCode; //物流码
            private String terminalNum; //设备号

            public OrderInfo(String securityCode, String manualCode, String logisticsCode, String terminalNum) {
                this.securityCode = securityCode;
                this.manualCode = manualCode;
                this.logisticsCode = logisticsCode;
                this.terminalNum = terminalNum;
            }

            public String getSecurityCode() {
                return securityCode;
            }

            public void setSecurityCode(String securityCode) {
                this.securityCode = securityCode;
            }

            public String getManualCode() {
                return manualCode;
            }

            public void setManualCode(String manualCode) {
                this.manualCode = manualCode;
            }

            public String getLogisticsCode() {
                return logisticsCode;
            }

            public void setLogisticsCode(String logisticsCode) {
                this.logisticsCode = logisticsCode;
            }

            public String getTerminalNum() {
                return terminalNum;
            }

            public void setTerminalNum(String terminalNum) {
                this.terminalNum = terminalNum;
            }
        }
    }


}

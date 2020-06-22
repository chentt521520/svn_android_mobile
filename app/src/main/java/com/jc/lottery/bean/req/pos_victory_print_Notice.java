package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2018/9/28 on 21:13.
 * @ description：打印通知
 * @ author: vchao
 */
public class pos_victory_print_Notice {

    /**
     * interfaceCode : k3Printer
     * requestTime : 1455606858
     * accountName : admin
     * data : {"printerInfo":{"orderCode":"201809281626003886765281","gameId":168,"drawNumber":"180928048","gameAlias":"k3"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;
    private DataBean data;

    public pos_victory_print_Notice() {
    }

    public pos_victory_print_Notice(String accountName, String password, DataBean.OrderInfo orderInfo) {
        this.interfaceCode = "printNotice";
        this.channel = "3";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
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
         * printerInfo : {"orderCode":"201809281626003886765281","gameId":168,"drawNumber":"180928048","gameAlias":"k3"}
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

        public static class OrderInfo  {
            /**
             * orderCode : 201809281626003886765281
             * gameId : 168
             * drawNumber : 180928048
             * gameAlias : k3
             */

            private String issue_no;
            private String order_code;

            public OrderInfo(String issue_no, String order_code) {
                this.issue_no = issue_no;
                this.order_code = order_code;
            }

            public String getIssue_no() {
                return issue_no;
            }

            public void setIssue_no(String issue_no) {
                this.issue_no = issue_no;
            }

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }
        }
    }
}

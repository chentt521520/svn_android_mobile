package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2018/9/28 on 21:13.
 * @ description：打印通知
 * @ author: vchao
 */
public class pos_print_Notice {

    /**
     * interfaceCode : k3Printer
     * requestTime : 1455606858
     * accountName : admin
     * data : {"printerInfo":{"orderCode":"201809281626003886765281","gameId":168,"drawNumber":"180928048","gameAlias":"k3"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public pos_print_Notice() {
    }

    public pos_print_Notice(String interfaceCode, String accountName, String orderCode, int gameId, String drawNumber, String gameAlias) {
        this.interfaceCode = interfaceCode;
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = new DataBean(orderCode, drawNumber, gameAlias);
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

        private PrinterInfoBean printerInfo;

        public DataBean(String orderCode, String drawNumber, String gameAlias) {
            this.printerInfo = new PrinterInfoBean(orderCode, drawNumber, gameAlias);
        }

        public PrinterInfoBean getPrinterInfo() {
            return printerInfo;
        }

        public void setPrinterInfo(PrinterInfoBean printerInfo) {
            this.printerInfo = printerInfo;
        }

        public static class PrinterInfoBean {
            /**
             * orderCode : 201809281626003886765281
             * gameId : 168
             * drawNumber : 180928048
             * gameAlias : k3
             */

            private String orderCode;
//            private int gameId;
            private String drawNumber;
            private String gameAlias;

            public PrinterInfoBean(String orderCode, String drawNumber, String gameAlias) {
                this.orderCode = orderCode;
                this.drawNumber = drawNumber;
                this.gameAlias = gameAlias;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }


            public String getDrawNumber() {
                return drawNumber;
            }

            public void setDrawNumber(String drawNumber) {
                this.drawNumber = drawNumber;
            }

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }
        }
    }
}

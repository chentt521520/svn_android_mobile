package com.jc.lottery.bean.resp;

/**
 * @ Create_time: 2018/9/28 on 21:22.
 * @ description：打印通知
 * @ author: vchao
 */
public class Resp_kuai3_Notice {

    /**
     * code : 00000
     * data : {"printerInfo":{"orderCode":"201809281626003886765281","gameId":168,"drawNumber":"180928048","gameAlias":"k3"}}
     * message : 出票成功!
     * state : 00
     */

    private String code;
    private DataBean data;
    private String message;
    private String state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * printerInfo : {"orderCode":"201809281626003886765281","gameId":168,"drawNumber":"180928048","gameAlias":"k3"}
         */

        private PrinterInfoBean printerInfo;

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
            private int gameId;
            private String drawNumber;
            private String gameAlias;

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public int getGameId() {
                return gameId;
            }

            public void setGameId(int gameId) {
                this.gameId = gameId;
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

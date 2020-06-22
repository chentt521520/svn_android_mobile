package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号重复状态查询
 * @ author: lr
 */
public class pos_GetQueryBookNo {
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

    public pos_GetQueryBookNo(String accountName, String password , String channel, DataBean data) {
        this.interfaceCode = "getBookNo";
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
        private BookInfo bookInfo;

        public DataBean(BookInfo bookInfo) {
            this.bookInfo = bookInfo;
        }

        public BookInfo getBookInfo() {
            return bookInfo;
        }

        public void setBookInfo(BookInfo bookInfo) {
            this.bookInfo = bookInfo;
        }
    }

    public static class BookInfo{
        private String gameAlias;
        private String schemeCode;
        private String schemeNum;
        private String bookNum;
        private String cartonNo;
        private String bookStart;
        private String bookEnd;

        public BookInfo(String gameAlias, String schemeCode, String schemeNum, String bookNum, String cartonNo, String bookStart, String bookEnd) {
            this.gameAlias = gameAlias;
            this.schemeCode = schemeCode;
            this.schemeNum = schemeNum;
            this.bookNum = bookNum;
            this.cartonNo = cartonNo;
            this.bookStart = bookStart;
            this.bookEnd = bookEnd;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public String getSchemeCode() {
            return schemeCode;
        }

        public void setSchemeCode(String schemeCode) {
            this.schemeCode = schemeCode;
        }

        public String getSchemeNum() {
            return schemeNum;
        }

        public void setSchemeNum(String schemeNum) {
            this.schemeNum = schemeNum;
        }

        public String getBookNum() {
            return bookNum;
        }

        public void setBookNum(String bookNum) {
            this.bookNum = bookNum;
        }

        public String getCartonNo() {
            return cartonNo;
        }

        public void setCartonNo(String cartonNo) {
            this.cartonNo = cartonNo;
        }

        public String getBookStart() {
            return bookStart;
        }

        public void setBookStart(String bookStart) {
            this.bookStart = bookStart;
        }

        public String getBookEnd() {
            return bookEnd;
        }

        public void setBookEnd(String bookEnd) {
            this.bookEnd = bookEnd;
        }
    }

}

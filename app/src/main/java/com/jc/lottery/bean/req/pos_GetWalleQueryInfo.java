package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：钱包流水查询
 * @ author: lr
 */
public class pos_GetWalleQueryInfo {
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
    private DataBean data;

    public pos_GetWalleQueryInfo(String accountName, DataBean data) {
        this.interfaceCode = "detailQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public static class DataBean {
        /**
         * rechargeCardInfo : {"gameAlias": "jkc"}
         */
        private WalleInfo walleInfo;

        public DataBean(WalleInfo walleInfo) {
            this.walleInfo = walleInfo;
        }

        public WalleInfo getWalleInfo() {
            return walleInfo;
        }

        public void setWalleInfo(WalleInfo walleInfo) {
            this.walleInfo = walleInfo;
        }
    }

    public static class WalleInfo{
        private String walleType;
        private String type;
        private String pageNo;
        private String startTime;
        private String endTime;
        private String date;

        public WalleInfo(String walleType, String type, String pageNo, String startTime, String endTime, String date) {
            this.walleType = walleType;
            this.type = type;
            this.pageNo = pageNo;
            this.startTime = startTime;
            this.endTime = endTime;
            this.date = date;
        }

        public String getWalleType() {
            return walleType;
        }

        public void setWalleType(String walleType) {
            this.walleType = walleType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}

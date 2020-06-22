package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/11/06 on 14:49.
 * @ description：兑奖查询
 * @ author: lr
 */
public class pos_listQuery {
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
    private String channel;
    private DataBean data;

    public pos_listQuery(String accountName, String password, DataBean data) {
        this.interfaceCode = "listQuery";
        this.channel = "3";
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
        private NoticeInfo noticeInfo;

        public DataBean(NoticeInfo noticeInfo) {
            this.noticeInfo = noticeInfo;
        }
    }

    public static class NoticeInfo{
        private String issue_no;

        public NoticeInfo(String issue_no) {
            this.issue_no = issue_no;
        }
    }

}

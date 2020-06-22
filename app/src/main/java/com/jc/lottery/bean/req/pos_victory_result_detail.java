package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/1/8 on 16:04.
 * @ description：开奖信息详情页面
 * @ author: vchao
 */
public class pos_victory_result_detail {

    /**
     * interfaceCode : drawNoticeQuery
     * requestTime : 1525743539
     * accountName : zhengweichao
     * data : {"colorPeriodInfo":{"gameAlias":"k3","draw":"181227011"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;
    private DataBean data;

    public pos_victory_result_detail(String accountName,String password, String issue_no) {
        this.interfaceCode = "detail";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = "3";
        DataBean.NoticeInfo noticeInfo = new DataBean.NoticeInfo(issue_no);
        this.data = new DataBean(noticeInfo);
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
         * colorPeriodInfo : {"gameAlias":"k3","draw":"181227011"}
         */

        private NoticeInfo noticeInfo;

        public DataBean(NoticeInfo noticeInfo) {
            this.noticeInfo = noticeInfo;
        }

        public NoticeInfo getNoticeInfo() {
            return noticeInfo;
        }

        public void setNoticeInfo(NoticeInfo noticeInfo) {
            this.noticeInfo = noticeInfo;
        }

        public static class NoticeInfo {
            /**
             * gameAlias : k3
             * draw : 181227011
             */

            private String issue_no;

            public NoticeInfo(String issue_no) {
                this.issue_no = issue_no;
            }
        }
    }
}

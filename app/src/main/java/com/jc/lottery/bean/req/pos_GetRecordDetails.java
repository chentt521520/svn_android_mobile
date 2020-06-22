package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：兑奖
 * @ author: lr
 */
public class pos_GetRecordDetails {
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

    public pos_GetRecordDetails(String accountName, String password , String channel, DataBean.RecordDetailsInfo recordDetailsInfo) {
        this.interfaceCode = "createOrder";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = new DataBean(recordDetailsInfo);
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
        private RecordDetailsInfo recordDetailsInfo;

        public DataBean(RecordDetailsInfo recordDetailsInfo) {
            this.recordDetailsInfo = recordDetailsInfo;
        }

        public RecordDetailsInfo getRecordDetailsInfo() {
            return recordDetailsInfo;
        }

        public void setRecordDetailsInfo(RecordDetailsInfo recordDetailsInfo) {
            this.recordDetailsInfo = recordDetailsInfo;
        }

        public static class RecordDetailsInfo{

            private int recordDetailsId;
            private int pageNo;

            public RecordDetailsInfo(int recordDetailsId, int pageNo) {
                this.recordDetailsId = recordDetailsId;
                this.pageNo = pageNo;
            }

            public int getRecordDetailsId() {
                return recordDetailsId;
            }

            public void setRecordDetailsId(int recordDetailsId) {
                this.recordDetailsId = recordDetailsId;
            }

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }
        }
    }
}

package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：兑奖
 * @ author: lr
 */
public class pos_GetElectronicLiveVideo {
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

    public pos_GetElectronicLiveVideo(String accountName,DrawInfo drawInfo) {
        this.interfaceCode = "liveVideo";
        this.accountName = accountName;
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.data = new DataBean(drawInfo);
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

    public static class DataBean {
        private DrawInfo requestInfo;

        public DataBean(DrawInfo requestInfo) {
            this.requestInfo = requestInfo;
        }
    }

    public static class DrawInfo{

        private String drawNumber;
        private String gameAlias;
        private Long timeStamp;

        public DrawInfo(String drawNumber, String gameAlias, Long timeStamp) {
            this.drawNumber = drawNumber;
            this.gameAlias = gameAlias;
            this.timeStamp = timeStamp;
        }
    }
}

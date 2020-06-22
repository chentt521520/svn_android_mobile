package com.jc.lottery.bean.req;


import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/07/03 on 14:49.
 * @ description：本号查询
 * @ author: lr
 */
public class pos_GetQueryGameList {
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

    public pos_GetQueryGameList(String accountName, DataBean data) {
        this.interfaceCode = "querGameList";
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
        private InitGameReqInfo initGameReq;

        public DataBean(InitGameReqInfo initGameReq) {
            this.initGameReq = initGameReq;
        }

        public InitGameReqInfo getInitGameReq() {
            return initGameReq;
        }

        public void setInitGameReq(InitGameReqInfo initGameReq) {
            this.initGameReq = initGameReq;
        }
    }

    public static class InitGameReqInfo{
        private String gameName;
        private String launch;
        private String fuzzySearch;

        public InitGameReqInfo(String gameName, String launch, String fuzzySearch) {
            this.gameName = gameName;
            this.launch = launch;
            this.fuzzySearch = fuzzySearch;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getLaunch() {
            return launch;
        }

        public void setLaunch(String launch) {
            this.launch = launch;
        }

        public String getFuzzySearch() {
            return fuzzySearch;
        }

        public void setFuzzySearch(String fuzzySearch) {
            this.fuzzySearch = fuzzySearch;
        }
    }

}

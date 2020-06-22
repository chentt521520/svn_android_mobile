package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2018/10/11 on 17:35.
 * @ description：规则查询 请求
 * @ author: vchao
 */
public class pos_findRule {

    /**
     * interfaceCode : findRule
     * requestTime : 1534917840
     * accountName : ceshi
     * data : {"ruleInfo":{"gameAlias":"k3"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public pos_findRule(String accountName, String gameAlias) {
        this.interfaceCode = "findRule";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = new DataBean(gameAlias);
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
         * ruleInfo : {"gameAlias":"k3"}
         */

        private RuleInfoBean ruleInfo;

        public DataBean(String gameAlias) {
            this.ruleInfo = new RuleInfoBean(gameAlias);
        }

        public RuleInfoBean getRuleInfo() {
            return ruleInfo;
        }

        public void setRuleInfo(RuleInfoBean ruleInfo) {
            this.ruleInfo = ruleInfo;
        }

        public static class RuleInfoBean {
            /**
             * gameAlias : k3
             */

            private String gameAlias;

            public RuleInfoBean(String gameAlias) {
                this.gameAlias = gameAlias;
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

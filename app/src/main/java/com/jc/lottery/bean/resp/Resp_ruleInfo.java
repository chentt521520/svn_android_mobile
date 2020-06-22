package com.jc.lottery.bean.resp;

import java.util.List;

/**
 * @ Create_time: 2018/10/11 on 18:03.
 * @ description：获取规则
 * @ author: vchao
 */
public class Resp_ruleInfo {

    /**
     * code : 00000
     * data : {"ruleInfo":{"gameAlias":"k3"}}
     * message : 查询规则成功!
     * ruleList : [{"number":"20","ruleName":"最小投注金额","ruleNum":"R007"},{"number":"99","ruleName":"注数","ruleNum":"R001"},{"number":"99","ruleName":"倍数","ruleNum":"R002"},{"number":"12","ruleName":"期数","ruleNum":"R003"},{"number":"5","ruleName":"不允许投注倒计时","ruleNum":"R006"},{"number":"5","ruleName":"退票时间","ruleNum":"R008"},{"number":"580000","ruleName":"最高中奖奖金","ruleNum":"R004"}]
     * state : 00
     */

    private String code;
    private DataBean data;
    private String message;
    private String state;
    private List<RuleListBean> ruleList;

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

    public List<RuleListBean> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleListBean> ruleList) {
        this.ruleList = ruleList;
    }

    public static class DataBean {
        /**
         * ruleInfo : {"gameAlias":"k3"}
         */

        private RuleInfoBean ruleInfo;

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

            public String getGameAlias() {
                return gameAlias;
            }

            public void setGameAlias(String gameAlias) {
                this.gameAlias = gameAlias;
            }
        }
    }

    public static class RuleListBean {
        /**
         * number : 20
         * ruleName : 最小投注金额
         * ruleNum : R007
         */

        private String number;
        private String ruleName;
        private String ruleNum;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getRuleNum() {
            return ruleNum;
        }

        public void setRuleNum(String ruleNum) {
            this.ruleNum = ruleNum;
        }
    }
}

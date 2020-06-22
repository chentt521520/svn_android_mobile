package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2018/10/11 on 15:04.
 * @ description：
 * @ author: vchao
 */
public class pos_user_type {

    /**
     * interfaceCode : userTypeQuery
     * requestTime : 1455606858
     * accountName : admin
     * data : {"userInfo":{"userPass":"123456"}}
     */

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public pos_user_type(String accountName, String userPass) {
        this.interfaceCode = "userTypeQuery";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.data = new DataBean(userPass);
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
         * userInfo : {"userPass":"123456"}
         */

        private UserInfoBean userInfo;

        public DataBean(String userPass) {
            this.userInfo = new UserInfoBean(userPass);
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * userPass : 123456
             */

            private String userPass;

            public UserInfoBean(String userPass) {
                this.userPass = userPass;
            }

            public String getUserPass() {
                return userPass;
            }

            public void setUserPass(String userPass) {
                this.userPass = userPass;
            }
        }
    }
}

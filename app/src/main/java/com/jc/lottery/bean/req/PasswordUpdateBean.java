package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

/**
 * @ Create_time: 2019/4/25 on 10:38.
 * @ description：YoPayments充值
 * @ author: lr
 */
public class PasswordUpdateBean {

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private DataBean data;

    public PasswordUpdateBean(String accountName, String oldPassword, String newPassword) {
        this.interfaceCode = "passwordUpdate";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        DataBean dataBean = new DataBean(oldPassword, newPassword);
        this.data = dataBean;
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

        private UserInfoBean userInfo;

        public DataBean(String oldPassword, String newPassword) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setOldPassword(oldPassword);
            userInfoBean.setNewPassword(newPassword);
            this.userInfo = userInfoBean;
        }
        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {

            private String oldPassword;
            private String newPassword;

            public String getOldPassword() {
                return oldPassword;
            }

            public void setOldPassword(String oldPassword) {
                this.oldPassword = oldPassword;
            }

            public String getNewPassword() {
                return newPassword;
            }

            public void setNewPassword(String newPassword) {
                this.newPassword = newPassword;
            }

        }
    }
}

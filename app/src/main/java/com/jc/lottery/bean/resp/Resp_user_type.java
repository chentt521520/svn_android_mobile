package com.jc.lottery.bean.resp;

/**
 * @ Create_time: 2018/10/11 on 15:07.
 * @ description：查询用户类型
 * @ author: vchao
 */
public class Resp_user_type {

    /**
     * code : 00000
     * message : 用户信息查询成功!
     * state : 00
     * userId : 4
     * userName : admin
     * userType : 00
     */

    private String code;
    private String message;
    private String state;
    private int userId;
    private String userName;
    private String userType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

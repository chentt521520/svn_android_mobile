package com.jc.lottery.bean.resp;

/**
 * @ Create_time: 2018/9/30 on 17:44.
 * @ description：时间校准
 * @ author: vchao
 */
public class Resp_timeCalibration {

    /**
     * code : 00000
     * message : 获取成功
     * state : 00
     * timeStamp : 1538299693353
     */

    private String code;
    private String message;
    private String state;
    private long timeStamp;

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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

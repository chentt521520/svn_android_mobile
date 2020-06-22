package com.jc.lottery.bean.type;

import java.io.Serializable;

public class YYRechargesMessage implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//{"mobile":"","active":"0","idcard":"","pan":"","status":"_0000","message":"success"}
	private String mobile;
	private int active;
	private String idcard;
	private String pan;
	private String status;
	private String message;
	private String success;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	
}

package com.jc.lottery.bean.type;

import java.io.Serializable;

public class YYRecharges implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//{"message":"success","ordernumber":"1257301316018618","status":"_0000"}
	private String status;
	private String message;
	private String ordernumber;
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
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	
}

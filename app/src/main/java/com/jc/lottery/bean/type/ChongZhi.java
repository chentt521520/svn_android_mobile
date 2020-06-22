package com.jc.lottery.bean.type;

import java.io.Serializable;


public class ChongZhi implements LotteryType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ordernumber;
	private String status;
	private String message;
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
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
	
	
	
	
}

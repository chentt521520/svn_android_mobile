package com.jc.lottery.bean.type;

import java.io.Serializable;

public class BallDBLottery implements LotteryType,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String code;
	private String type;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}

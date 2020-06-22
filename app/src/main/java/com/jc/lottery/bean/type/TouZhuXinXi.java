package com.jc.lottery.bean.type;

import java.io.Serializable;

public class TouZhuXinXi implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String termNo; //期号
	private String lotterybalance; //彩金
	private String lsbalance; //可提现余额
	private String status;
//{"termNo":"2012045","lotterybalance":"0.00","status":"_0000","lsbalance":"2189.00"}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getLotterybalance() {
		return lotterybalance;
	}
	public void setLotterybalance(String lotterybalance) {
		this.lotterybalance = lotterybalance;
	}
	public String getLsbalance() {
		return lsbalance;
	}
	public void setLsbalance(String lsbalance) {
		this.lsbalance = lsbalance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

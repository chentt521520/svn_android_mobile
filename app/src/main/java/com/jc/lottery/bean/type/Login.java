package com.jc.lottery.bean.type;

import java.io.Serializable;

public class Login implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String lotterybalance;
	private String allWinMoney;
	private String userId;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getLotterybalance() {
		return lotterybalance;
	}
	public void setLotterybalance(String lotterybalance) {
		this.lotterybalance = lotterybalance;
	}
	public String getAllWinMoney() {
		return allWinMoney;
	}
	public void setAllWinMoney(String allWinMoney) {
		this.allWinMoney = allWinMoney;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

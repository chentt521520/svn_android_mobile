package com.jc.lottery.bean.type;

import java.io.Serializable;

public class RechargesRecordsData implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String channel;
	
	private String money;
	
	private String feeMoney;
	
	private String time;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getFeeMoney() {
		return feeMoney;
	}

	public void setFeeMoney(String feeMoney) {
		this.feeMoney = feeMoney;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}

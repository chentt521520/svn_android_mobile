package com.jc.lottery.bean.type;

import java.io.Serializable;

public class UesDetail implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String time;//表示交易时间
	private String businessType;//表示收入或者支出
	private String tradeType;//表示交易类型
	private String tradeMoney;//表示交易金额
	private String balance;//表示剩余金额
	private String freezeMoney;//表示冻结金额
	private String no;//表示序列号
	private String description;//表示交易描述
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeMoney() {
		return tradeMoney;
	}
	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getFreezeMoney() {
		return freezeMoney;
	}
	public void setFreezeMoney(String freezeMoney) {
		this.freezeMoney = freezeMoney;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	 
}

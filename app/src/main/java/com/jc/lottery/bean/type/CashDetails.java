package com.jc.lottery.bean.type;

import java.io.Serializable;

public class CashDetails implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lotterybalance;
	private String bankNo;
	private String bank;
	private String bankName;
	private String subbranch;
	private String city;
	private String province;
	private String lsbalance;
	public String getLotterybalance() {
		return lotterybalance;
	}
	public void setLotterybalance(String lotterybalance) {
		this.lotterybalance = lotterybalance;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getSubbranch() {
		return subbranch;
	}
	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLsbalance() {
		return lsbalance;
	}
	public void setLsbalance(String lsbalance) {
		this.lsbalance = lsbalance;
	}
	
	
}

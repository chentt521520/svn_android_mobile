package com.jc.lottery.bean.type;

import java.io.Serializable;

public class Betting implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lotteryType;
	private String term;
	private String codes;
	private String money;
	private String multiple;// 倍数
	private String oneMoney;
	private String status;
	private String balance;
	private String from;
	private String chaseMoney;// 投注总额
	private String num;

	public Betting() {
	}

	public Betting(String lotteryType, String term, String codes, String money, String multiple, String oneMoney, String status, String balance, String from, String chaseMoney, String num) {
		this.lotteryType = lotteryType;
		this.term = term;
		this.codes = codes;
		this.money = money;
		this.multiple = multiple;
		this.oneMoney = oneMoney;
		this.status = status;
		this.balance = balance;
		this.from = from;
		this.chaseMoney = chaseMoney;
		this.num = num;
	}

	public String getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getOneMoney() {
		return oneMoney;
	}
	public void setOneMoney(String oneMoney) {
		this.oneMoney = oneMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getChaseMoney() {
		return chaseMoney;
	}
	public void setChaseMoney(String chaseMoney) {
		this.chaseMoney = chaseMoney;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}

package com.jc.lottery.bean.type;

import java.io.Serializable;

public class OrderInfoBean implements LotteryType, Serializable{
	/**
	 * 生成订单
	 */
	private String status;
	private String balance;
	private String order_no;
	private String money;

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

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
}

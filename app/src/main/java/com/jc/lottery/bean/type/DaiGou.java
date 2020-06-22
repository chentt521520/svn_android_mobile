package com.jc.lottery.bean.type;

import java.io.Serializable;

public class DaiGou implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;//彩种
	private String term_no;//期号
	private String tzfs;//投注方式
	private String time;//交易时间
	private String amount;//认购金额
	private String status;//票状态
	private String result;//中奖状态
	private String ticketmessage;
	private String win_tax_money;//我的奖金
	private String order_id;//方案号
	private String openPrizeTime;
	private String order_num; // 订单号
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTerm_no() {
		return term_no;
	}
	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}
	public String getTzfs() {
		return tzfs;
	}
	public void setTzfs(String tzfs) {
		this.tzfs = tzfs;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getWin_tax_money() {
		return win_tax_money;
	}
	public void setWin_tax_money(String win_tax_money) {
		this.win_tax_money = win_tax_money;
	}
	
	public String getOpenPrizeTime() {
		return openPrizeTime;
	}
	public void setOpenPrizeTime(String openPrizeTime) {
		this.openPrizeTime = openPrizeTime;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTicketmessage() {
		return ticketmessage;
	}

	public void setTicketmessage(String ticketmessage) {
		this.ticketmessage = ticketmessage;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
}

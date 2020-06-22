package com.jc.lottery.bean.type;

import java.io.Serializable;

public class DaiGouXiangQing implements LotteryType,Serializable{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = -7695378550530932460L;
	private String termNo;//期号
	private String openPrizeTime;//开奖时间   字符串  格式YYYY-MM-DD
	private String multiple;//倍数
	private String amount;//彩票金额
	private String out_order_time;//下单时间
	private String order_num;//订单号
	private String cg;
	public String getCg() {
		return cg;
	}
	public void setCg(String cg) {
		this.cg = cg;
	}
	private String result;//开奖结果
	private String winMoney;//中奖金额
	private String zhushu;//总的注数
	private Group<MyLottery> myLotteries; //我选择号的详情(是个二位数组
	private Group<MyJczqDetailLottery> myJczqLotteries;
	public Group<MyJczqDetailLottery> getMyJczqLotteries() {
		return myJczqLotteries;
	}
	public void setMyJczqLotteries(Group<MyJczqDetailLottery> myJczqLotteries) {
		this.myJczqLotteries = myJczqLotteries;
	}
	private String type;
	
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getOpenPrizeTime() {
		return openPrizeTime;
	}
	public void setOpenPrizeTime(String openPrizeTime) {
		this.openPrizeTime = openPrizeTime;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getWinMoney() {
		return winMoney;
	}
	public void setWinMoney(String winMoney) {
		this.winMoney = winMoney;
	}
	public String getZhushu() {
		return zhushu;
	}
	public void setZhushu(String zhushu) {
		this.zhushu = zhushu;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Group<MyLottery> getMyLotteries() {
		return myLotteries;
	}
	public void setMyLotteries(Group<MyLottery> myLotteries) {
		this.myLotteries = myLotteries;
	}

	public String getOut_order_time() {
		return out_order_time;
	}

	public void setOut_order_time(String out_order_time) {
		this.out_order_time = out_order_time;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
}

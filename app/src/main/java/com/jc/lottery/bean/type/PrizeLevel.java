package com.jc.lottery.bean.type;

public class PrizeLevel implements LotteryType{
	private String name;
	private String betnum;
	private String prize;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBetnum() {
		return betnum;
	}
	public void setBetnum(String betnum) {
		this.betnum = betnum;
	}
	public String getPrize() {
		return prize;
	}
	public void setPrize(String prize) {
		this.prize = prize;
	}
	
}

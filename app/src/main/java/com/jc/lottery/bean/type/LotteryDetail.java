package com.jc.lottery.bean.type;

public class LotteryDetail implements LotteryType {
	private String stopSendPrizeTime;
	private String action;
	private String total_sale;
	private String prize_pool;
	private Group<PrizeLevel> group;
	
	public String getStopSendPrizeTime() {
		return stopSendPrizeTime;
	}
	public void setStopSendPrizeTime(String stopSendPrizeTime) {
		this.stopSendPrizeTime = stopSendPrizeTime;
	}

	public String getTotal_sale() {
		return total_sale;
	}

	public void setTotal_sale(String total_sale) {
		this.total_sale = total_sale;
	}

	public String getPrize_pool() {
		return prize_pool;
	}

	public void setPrize_pool(String prize_pool) {
		this.prize_pool = prize_pool;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Group<PrizeLevel> getGroup() {
		return group;
	}
	public void setGroup(Group<PrizeLevel> group) {
		this.group = group;
	}
	
}

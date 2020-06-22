package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * 获取双色球/3D等期号截止日期信息
 */
public class SelectOpenPrizeTimeBean implements LotteryType, Serializable{
	private static final long serialVersionUID = 1L;
	private String term_no; //期号
	private String stop_sale_time; //彩金
	private String status;
	/**
	 * {"status":"_0000",
	 * "term_no":"2017061",
	 * "stop_sale_time":"2017-05-28 19:55:00"}
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getTerm_no() {
		return term_no;
	}

	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}

	public String getStop_sale_time() {
		return stop_sale_time;
	}

	public void setStop_sale_time(String stop_sale_time) {
		this.stop_sale_time = stop_sale_time;
	}
}

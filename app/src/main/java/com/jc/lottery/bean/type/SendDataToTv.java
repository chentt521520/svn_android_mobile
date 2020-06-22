package com.jc.lottery.bean.type;

import java.io.Serializable;

public class SendDataToTv implements LotteryType, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	private String ID;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

package com.jc.lottery.bean.type;

import java.io.Serializable;

public class RechargesRecords implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String count;
	private String offset;
	private Group<RechargesRecordsData> dataGroup;
	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public String getOffset() {
		return offset;
	}
	
	public void setOffset(String offset) {
		this.offset = offset;
	}

	public Group<RechargesRecordsData> getDataGroup() {
		return dataGroup;
	}

	public void setDataGroup(Group<RechargesRecordsData> dataGroup) {
		this.dataGroup = dataGroup;
	}
	
	
	
}

package com.jc.lottery.bean.type;

import java.io.Serializable;

public class ZhuiHao implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String count;
	private String offset;
	private Group<ZhuiHaoData> dataGroup;
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

	public Group<ZhuiHaoData> getDataGroup() {
		return dataGroup;
	}

	public void setDataGroup(Group<ZhuiHaoData> dataGroup) {
		this.dataGroup = dataGroup;
	}
	
	
	
}

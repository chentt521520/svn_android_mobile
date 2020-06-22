package com.jc.lottery.bean.type;

import java.io.Serializable;

public class DeadLine implements LotteryType, Serializable{
	private static final long serialVersionUID = 1L;
	private long deadLine;
	private int max;
	private long openPrizeTime;
	private String stopSaleTime;
	private String tage;
	private String termNo;
	private String type;

	public long getDeadLine()
	{
		return this.deadLine;
	}

	public int getMax()
	{
		return this.max;
	}

	public long getOpenPrizeTime()
	{
		return this.openPrizeTime;
	}

	public String getStopSaleTime()
	{
		return this.stopSaleTime;
	}

	public String getTage()
	{
		return this.tage;
	}

	public String getTermNo()
	{
		return this.termNo;
	}

	public String getType()
	{
		return this.type;
	}

	public void setDeadLine(long paramLong)
	{
		this.deadLine = paramLong;
	}

	public void setMax(int paramInt)
	{
		this.max = paramInt;
	}

	public void setOpenPrizeTime(long paramLong)
	{
		this.openPrizeTime = paramLong;
	}

	public void setStopSaleTime(String paramString)
	{
		this.stopSaleTime = paramString;
	}

	public void setTage(String paramString)
	{
		this.tage = paramString;
	}

	public void setTermNo(String paramString)
	{
		this.termNo = paramString;
	}

	public void setType(String paramString)
	{
		this.type = paramString;
	}
}
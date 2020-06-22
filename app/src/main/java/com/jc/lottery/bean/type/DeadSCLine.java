package com.jc.lottery.bean.type;

import java.io.Serializable;

public class DeadSCLine implements LotteryType, Serializable{
	private static final long serialVersionUID = 1L;
	private long deadLine;
	private String max;
	private long openPrizeTime;
	private String stopSaleTime;
	private String tage;
	private String termNo;
	private String type;
	
	
	private String q1;
	public String getQ1() {
		return q1;
	}



	public void setQ1(String q1) {
		this.q1 = q1;
	}



	public String getWz() {
		return wz;
	}



	public void setWz(String wz) {
		this.wz = wz;
	}



	public String getDxjo() {
		return dxjo;
	}



	public void setDxjo(String dxjo) {
		this.dxjo = dxjo;
	}



	private String wz;
	private String dxjo;

	public long getDeadLine()
	{
		return this.deadLine;
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



	public String getMax() {
		return max;
	}



	public void setMax(String max) {
		this.max = max;
	}
}
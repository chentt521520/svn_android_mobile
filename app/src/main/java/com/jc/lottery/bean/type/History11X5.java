package com.jc.lottery.bean.type;

import java.io.Serializable;

public class History11X5 implements LotteryType, Serializable{
	private static final long serialVersionUID = 1L;
	private String cord;
	private String date;
	private String deadLine;
	private String name;
	private String termNo;
	private String type;

	public String getCord()
	{
		return this.cord;
	}

	public String getDate()
	{
		return this.date;
	}

	public String getDeadLine()
	{
		return this.deadLine;
	}

	public String getName()
	{
		return this.name;
	}

	public String getTermNo()
	{
		return this.termNo;
	}

	public String getType()
	{
		return this.type;
	}

	public void setCord(String cord)
	{
		this.cord = cord;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public void setDeadLine(String deadLine)
	{
		this.deadLine = deadLine;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setTermNo(String termNo)
	{
		this.termNo = termNo;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
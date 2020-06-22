package com.jc.lottery.bean.type;

import java.io.Serializable;

public class BetDataBean implements LotteryType, Serializable {
	/**
	 * 投注参数
	 */

	/*
	 * intent.setClass(this, MipcaActivityCapture.class);
	 * intent.putExtra("betType",
	 * TranslateLotteryType.changeToEn(type));//1彩票类型（扫码）
	 * intent.putExtra("issueNo", termNo); // 2期号（扫码）
	 * intent.putExtra("contents", strBeting); // 3选择的号码（扫码）
	 * intent.putExtra("money", zhifu); // 4钱数（扫码） intent.putExtra("multiples",
	 * zhushu); // 5注数（扫码） intent.putExtra("beishu", beishu); // 6倍数
	 * intent.putExtra("zhuihao",zhuihao); // 7追号 intent.putExtra("chaseMoney",
	 * chaseMoney); // 8订单金额
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String userType;
	private String betType;
	private String issueNo;
	private String betID;
	private String contents;
	private String money;
	private String multiples;   //倍数
	private String zhushu;   //注数
	private String zhuihao;
	private String chaseMoney;
	private String ID;
	private boolean yincang;

	public String getID() {
		return ID;
	}

	public boolean isYincang() {
		return yincang;
	}

	public void setYincang(boolean yincang) {
		this.yincang = yincang;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getBetID() {
		return betID;
	}

	public void setBetID(String betID) {
		this.betID = betID;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMultiples() {
		return multiples;
	}

	public void setMultiples(String multiples) {
		this.multiples = multiples;
	}

	public String getZhushu() {
		return zhushu;
	}

	public void setZhushu(String zhushu) {
		this.zhushu = zhushu;
	}

	public String getZhuihao() {
		return zhuihao;
	}

	public void setZhuihao(String zhuihao) {
		this.zhuihao = zhuihao;
	}

	public String getChaseMoney() {
		return chaseMoney;
	}

	public void setChaseMoney(String chaseMoney) {
		this.chaseMoney = chaseMoney;
	}

	@Override
	public String toString() {
		return "BetDataBean [username=" + username + ", password=" + password + ", userType=" + userType + ", betType="
				+ betType + ", issueNo=" + issueNo + ", betID=" + betID + ", contents=" + contents + ", money=" + money
				+ ", multiples=" + multiples + ", zhushu=" + zhushu + ", zhuihao=" + zhuihao + ", chaseMoney="
				+ chaseMoney + ", ID=" + ID + "]";
	}

	

	

}

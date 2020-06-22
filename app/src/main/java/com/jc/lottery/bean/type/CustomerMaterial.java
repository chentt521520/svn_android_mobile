package com.jc.lottery.bean.type;

import java.io.Serializable;

public class CustomerMaterial implements LotteryType, Serializable{
	/**
	 * {"realName":"",
	 * "email":"",
	 * "credentNo":"",
	 * "mobile_no":"13121372347",
	 * "username":"13121372347",
	 * "bank":"",
	 * "province":"",
	 * "city":"",
	 * "subbranch":"",
	 * "card":"",
	 * "headimgurl":"http:\/\/chu.mvschina.com\/upnew\/user\/437header.jpg",
	 * "lotterybalance":"0.00",
	 * "allWinMoney":null}
	 */
	private static final long serialVersionUID = 1L;
	private String realName;
	private String email;
	private String credentNo;
	private String mobile_no;
	private String username;
	private String bank;
	private String province;
	private String city;
	private String subbranch;
	private String card;
	private String headimgurl;
	private String lotterybalance;
	private String allWinMoney;
	private String share_monery;
	private String share_count_monery;
	private String share_num;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCredentNo() {
		return credentNo;
	}
	public void setCredentNo(String credentNo) {
		this.credentNo = credentNo;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getSubbranch() {
		return subbranch;
	}
	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getLotterybalance() {
		return lotterybalance;
	}

	public void setLotterybalance(String lotterybalance) {
		this.lotterybalance = lotterybalance;
	}

	public String getAllWinMoney() {
		return allWinMoney;
	}

	public void setAllWinMoney(String allWinMoney) {
		this.allWinMoney = allWinMoney;
	}

	public String getShare_monery() {
		return share_monery;
	}

	public void setShare_monery(String share_monery) {
		this.share_monery = share_monery;
	}

	public String getShare_count_monery() {
		return share_count_monery;
	}

	public void setShare_count_monery(String share_count_monery) {
		this.share_count_monery = share_count_monery;
	}

	public String getShare_num() {
		return share_num;
	}

	public void setShare_num(String share_num) {
		this.share_num = share_num;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
}

package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * 首页
 */
public class MainIndexInfoBean implements LotteryType, Serializable{
	private String public_list;
	private String ssq;
	private String sd;
	private String k3;
	private String yqyj;
	private String tjdj;
	private String tgrw;
	private String share;

	public String getPublic_list() {
		return public_list;
	}

	public void setPublic_list(String public_list) {
		this.public_list = public_list;
	}

	public String getSsq() {
		return ssq;
	}

	public void setSsq(String ssq) {
		this.ssq = ssq;
	}

	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	public String getK3() {
		return k3;
	}

	public void setK3(String k3) {
		this.k3 = k3;
	}

	public String getYqyj() {
		return yqyj;
	}

	public void setYqyj(String yqyj) {
		this.yqyj = yqyj;
	}

	public String getTjdj() {
		return tjdj;
	}

	public void setTjdj(String tjdj) {
		this.tjdj = tjdj;
	}

	public String getTgrw() {
		return tgrw;
	}

	public void setTgrw(String tgrw) {
		this.tgrw = tgrw;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}
}

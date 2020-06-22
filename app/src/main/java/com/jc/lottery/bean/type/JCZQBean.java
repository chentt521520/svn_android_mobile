package com.jc.lottery.bean.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JCZQBean implements LotteryType, Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isClickLeftUpView;
	private boolean isClickCenterUpView;
	private boolean isClickRightUpView;
	private boolean isClickLeftDownView;
	private boolean isClickCenterDownView;
	private boolean isClickRightDownView;
	private boolean isSelectItem;

	public boolean isSelectItem() {
		return isSelectItem;
	}

	public void setSelectItem(boolean isSelectItem) {
		this.isSelectItem = isSelectItem;
	}

	private boolean isClickFenxi;
	/**
	 * 比赛名称
	 */
	private String tv_spf_shuju;
	/**
	 * 比赛截止时间
	 */
	private String tv_spf_shijian;
	/**
	 * 主队名称
	 */
	private String tv_spf_frq_s_name;
	/**
	 * 主队概率
	 */
	private String tv_spf_frq_s_bf;

	/**
	 * 平概率
	 */
	private String tv_spf_frq_p_bf;
	/**
	 * 负队名称
	 */
	private String tv_spf_frq_f_name;
	/**
	 * 负队概率
	 */
	private String tv_spf_frq_f_bf;
	/**
	 * 负队让球概率
	 */
	private String tv_spf_rq_s_bf;
	/**
	 * 让球数目
	 */
	private String tv_spf_rq_number;
	/**
	 * 让球平概率
	 */
	private String tv_spf_rq_p_bf;

	/**
	 * 负队让球概率
	 */
	private String tv_spf_rq_f_bf;
	/**
	 * 每个时间场次
	 */
	private String childid;
	private List<String> list = new ArrayList<String>();
	/**
	 * 官方概率
	 */
	private String[] current_ratios;

	// 场次编号

	private String bout_index;
	private boolean isSpfOpen;
	private boolean isRqSpfOpen;
	

	public boolean isSpfOpen() {
		return isSpfOpen;
	}

	public void setSpfOpen(boolean isSpfOpen) {
		this.isSpfOpen = isSpfOpen;
	}

	public boolean isRqSpfOpen() {
		return isRqSpfOpen;
	}

	public void setRqSpfOpen(boolean isRqSpfOpen) {
		this.isRqSpfOpen = isRqSpfOpen;
	}

	public String getBout_index() {
		return bout_index;
	}

	public void setBout_index(String bout_index) {
		this.bout_index = bout_index;
	}

	public String[] getCurrent_ratios() {
		return current_ratios;
	}

	public void setCurrent_ratios(String[] current_ratios) {
		this.current_ratios = current_ratios;
	}

	public List<String> getList() {
		return list;
	}

	public boolean isClickLeftUpView() {
		return isClickLeftUpView;
	}

	public String getChildid() {
		return childid;
	}

	public void setChildid(String childid) {
		this.childid = childid;
	}

	public void setClickLeftUpView(boolean isClickLeftUpView) {
		this.isClickLeftUpView = isClickLeftUpView;
	}

	public boolean isClickCenterUpView() {
		return isClickCenterUpView;
	}

	public void setClickCenterUpView(boolean isClickCenterUpView) {
		this.isClickCenterUpView = isClickCenterUpView;
	}

	public boolean isClickRightUpView() {
		return isClickRightUpView;
	}

	public void setClickRightUpView(boolean isClickRightUpView) {
		this.isClickRightUpView = isClickRightUpView;
	}

	public boolean isClickLeftDownView() {
		return isClickLeftDownView;
	}

	public void setClickLeftDownView(boolean isClickLeftDownView) {
		this.isClickLeftDownView = isClickLeftDownView;
	}

	public boolean isClickCenterDownView() {
		return isClickCenterDownView;
	}

	public void setClickCenterDownView(boolean isClickCenterDownView) {
		this.isClickCenterDownView = isClickCenterDownView;
	}

	public boolean isClickRightDownView() {
		return isClickRightDownView;
	}

	public void setClickRightDownView(boolean isClickRightDownView) {
		this.isClickRightDownView = isClickRightDownView;
	}

	public boolean isClickFenxi() {
		return isClickFenxi;
	}

	public void setClickFenxi(boolean isClickFenxi) {
		this.isClickFenxi = isClickFenxi;
	}

	public String getTv_spf_shuju() {
		return tv_spf_shuju;
	}

	public void setTv_spf_shuju(String tv_spf_shuju) {
		this.tv_spf_shuju = tv_spf_shuju;
	}

	public String getTv_spf_shijian() {
		return tv_spf_shijian;
	}

	public void setTv_spf_shijian(String tv_spf_shijian) {
		this.tv_spf_shijian = tv_spf_shijian;
	}

	public String getTv_spf_frq_s_name() {
		return tv_spf_frq_s_name;
	}

	public void setTv_spf_frq_s_name(String tv_spf_frq_s_name) {
		this.tv_spf_frq_s_name = tv_spf_frq_s_name;
	}

	public String getTv_spf_frq_s_bf() {
		return tv_spf_frq_s_bf;
	}

	public void setTv_spf_frq_s_bf(String tv_spf_frq_s_bf) {
		this.tv_spf_frq_s_bf = tv_spf_frq_s_bf;
	}

	public String getTv_spf_frq_p_bf() {
		return tv_spf_frq_p_bf;
	}

	public void setTv_spf_frq_p_bf(String tv_spf_frq_p_bf) {
		this.tv_spf_frq_p_bf = tv_spf_frq_p_bf;
	}

	public String getTv_spf_frq_f_name() {
		return tv_spf_frq_f_name;
	}

	public void setTv_spf_frq_f_name(String tv_spf_frq_f_name) {
		this.tv_spf_frq_f_name = tv_spf_frq_f_name;
	}

	public String getTv_spf_frq_f_bf() {
		return tv_spf_frq_f_bf;
	}

	public void setTv_spf_frq_f_bf(String tv_spf_frq_f_bf) {
		this.tv_spf_frq_f_bf = tv_spf_frq_f_bf;
	}

	public String getTv_spf_rq_s_bf() {
		return tv_spf_rq_s_bf;
	}

	public void setTv_spf_rq_s_bf(String tv_spf_rq_s_bf) {
		this.tv_spf_rq_s_bf = tv_spf_rq_s_bf;
	}

	public String getTv_spf_rq_number() {
		return tv_spf_rq_number;
	}

	public void setTv_spf_rq_number(String tv_spf_rq_number) {
		this.tv_spf_rq_number = tv_spf_rq_number;
	}

	/**
	 * 比赛名称
	 */

	public String getTv_spf_rq_p_bf() {
		return tv_spf_rq_p_bf;
	}

	public void setTv_spf_rq_p_bf(String tv_spf_rq_p_bf) {
		this.tv_spf_rq_p_bf = tv_spf_rq_p_bf;
	}

	/**
	 * 比赛名称
	 */

	public String getTv_spf_rq_f_bf() {
		return tv_spf_rq_f_bf;
	}

	public void setTv_spf_rq_f_bf(String tv_spf_rq_f_bf) {
		this.tv_spf_rq_f_bf = tv_spf_rq_f_bf;
	}

	public void deleteData() {
		setClickLeftUpView(false);
		setClickCenterUpView(false);
		setClickRightUpView(false);
		setClickLeftDownView(false);
		setClickCenterDownView(false);
		setClickRightDownView(false);
	}
}

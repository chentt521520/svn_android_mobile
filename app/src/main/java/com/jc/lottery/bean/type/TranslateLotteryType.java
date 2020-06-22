package com.jc.lottery.bean.type;

public class TranslateLotteryType {
	
	public static String changeToEn(String type)
	{
		if (type.equals("双色球")) {
			type = "ssq";
		}else if (type.equals("福彩3d") || type.equals("福彩3D")) {
			type = "3d";
		}else if (type.equals("排列三")) {
			type = "pls";		
		}else if (type.equals("排列五")) {
			type = "plw";
		}else if (type.equals("大乐透")) {
			type = "dlt";
		}else if (type.equals("七星彩")) {
			type = "qxc";
		}else if (type.equals("七乐彩")) {
			type = "qlc";
		}else if(type.equals("幸运赛车")){
			type = "xysc";
		}else if(type.equals("11选5")){
			type = "l11x5";
		}else if(type.equals("竞彩足球")){
			type = "jczq";
		}else if(type.equals("足彩14场")){
			type = "14sfc";
		}else if(type.equals("任选9场")){
			type = "r9";
		}else if(type.equals("北京快3")){
			type = "bjks";
//			type = "gxk3";
		}else if(type.equals("PK10")){
			type = "PK10";
		}
		return type;
	}
	public static String k3ChangeToEn(String type)
	{
		if (type.equals("和值")) {
			type = "hz";
		}else if (type.equals("三同号通选")) {
			type = "sth_tx";
		}else if (type.equals("三同号")) {
			type = "sth_ds";		
		}else if (type.equals("三不同号")) {
			type = "sbt_ds";
		}else if (type.equals("三连号通选")) {
			type = "slh_tx";
		}else if (type.equals("二同号复选")) {
			type = "eth_fs";
		}else if (type.equals("二同号单选")) {
			type = "eth_ds";
		}else if(type.equals("二不同号")){
			type = "ebt_ds";
		}
		return type;
	}
	public static String changeToCh(String type)
	{
		if (type.equals("ssq")) {
			type = "双色球";
		}
		else if (type.equals("3d")) {
			type = "福彩3D";
		}
		else if (type.equals("pls")) {
			type = "排列三";		
		}
		else if (type.equals("plw")) {
			type = "排列五";
		}
		else if (type.equals("dlt")) {
			type = "大乐透";
		}
		else if (type.equals("qxc")) {
			type = "七星彩";
		}
		else if (type.equals("qlc")) {
			type = "七乐彩";
		}else if(type.equals("l11x5")){
			type = "11选5";
		}else if(type.equals("jczq")){
			type = "竞彩足球";
		}else if(type.equals("14sfc")){
			type = "足彩14场";
		}else if(type.equals("r9")){
			type = "任选9场";
		}else if(type.equals("xysc")){
			type = "幸运赛车";
		}else if(type.equals("bjks")) {
			type = "北京快3";
		}else if(type.equals("PK10")) {
			type = "PK10";
		}else {
			throw new RuntimeException("no such type");
		}
		return type;
	}
	
	public static String changeBet(String type, String bet)
	{
		if (type.equals("双色球") || type.equals("大乐透") || type.equals("七乐彩")) {
			bet = bet.replace(" ", "");
		}
		else if (type.equals("福彩3D") || type.equals("排列三") || type.equals("排列五") || type.equals("七星彩")) {
			bet = bet.replace(",", "").replace("|", ",");
		}
		else {
			throw new RuntimeException("no such type");
		}
		return bet;
	}
}

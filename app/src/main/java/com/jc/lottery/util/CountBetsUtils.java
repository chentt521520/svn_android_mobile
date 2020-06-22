package com.jc.lottery.util;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

/**
 * 计算投注注数工具类
 */
public class CountBetsUtils {

	//求排列数
	public static int A(int up, int bellow) {
		int result = 1;
		for (int i = up; i > 0; i--) {
			result *= bellow;
			bellow--;
		}
		return result;
	}

	//求组合数，这个也不需要了。定义式，不使用互补率
	public static int C2(int up, int below) {
//       int denominator=factorial(up);//分母up的阶乘
		//分母
		int denominator = A(up, up);//A(6,6)就是求6*5*4*3*2*1,也就是求6的阶乘
		//分子
		int numerator = A(up, below);//分子的排列数
		return numerator / denominator;
	}

	//应用组合数的互补率简化计算量
	public static int C(int up, int below) {
		int helf = below / 2;
		if (up > helf) {
			System.out.print(up + "---->");
			up = below - up;
			System.out.print(up + "\n");

		}
		int denominator = A(up, up);//A(6,6)就是求6*5*4*3*2*1,也就是求6的阶乘
		//分子
		int numerator = A(up, below);//分子的排列数
		return numerator / denominator;
	}

	/*
	 * 求阶乘
	 */
	public static int math1(int n,int m){
		if(n == m){
			return 1;
		}
		int sam = n;
		while(n != m){
			sam *= (n-1);
			n--;
		}
		return sam;
	}

	/*
	 * 求n中选m个
	 */
	public static int math2(int n,int m){
		if(m==1){
			return n;
		}
		return math1(n,(n-m+1))/math1(m,1);
	}

	public static void whichGrid(int position, List<String> nums, List<String> select,
			BaseAdapter adapter) {
		String p = nums.get(position);
		if (select.contains(p)) {
			select.remove(p);
		} else {
			select.add(p);
		}
		adapter.notifyDataSetChanged();
	}

	//为3d等调用的(为输入0, 1 , 2)
	public static void getnumsOther(int num, List<String> nums) {
		for (int i = 0; i <= num; i++) {
			nums.add(String.valueOf(i));
		}
	}

	//双色球, 大乐透， 七乐彩调用的(为输入01, 02等)
	public static void getnums(int num, List<String> nums) {
		for (int i = 1; i <= num; i++) {
			if (i < 10) {
				nums.add("0" + i);
			} else {
				nums.add(String.valueOf(i));
			}
		}
	}

	//PK10调用的(为输入01, 02等)
	public static void getPK10Nums(int num, List<String> nums) {
		for (int i = 6; i <= num; i++) {
			if (i < 10) {
				nums.add("0" + i);
			} else {
				nums.add(String.valueOf(i));
			}
		}
	}

	//赛车调用的(为输入01, 02等)
	public static void getnumsSCWZ(int num, List<String> nums) {
		for (int i = 1; i <= num; i++) {
			nums.add("0" + i);
		}
	}

	//调用的
	public static void getnumsOth(int num, List<String> nums) {
		for (int i = 1; i <= num; i++) {
			nums.add(String.valueOf(i));
		}
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}

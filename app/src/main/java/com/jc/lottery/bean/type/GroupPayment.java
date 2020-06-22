package com.jc.lottery.bean.type;

public class GroupPayment {
	private static GroupPayment instance_  = new GroupPayment();
	private Group<Payment> group = new Group<Payment>();
	
	private GroupPayment(){
		
	}
	public static GroupPayment instance() {
		return instance_;
	}
	public Group<Payment> getGroup() {
		return group;
	}
	
	
}

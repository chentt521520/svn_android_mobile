package com.jc.lottery.bean.type;

import java.io.Serializable;

public class GroupInfo implements LotteryType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BrowserInfo group;
	private Group<JCZQBean> zqjcWanFas;
	 public GroupInfo() {
     }
     
     public void add(JCZQBean info){
    	 zqjcWanFas.add(info);
     }
     
     public void remove(BrowserInfo info){
    	 zqjcWanFas.remove(info);
     }
     
     public void remove(int index){
    	 zqjcWanFas.remove(index);
     }
     
     public int getChildSize(){
             return zqjcWanFas.size();
     }
     
     public JCZQBean getChild(int index){
             return zqjcWanFas.get(index);
     }

     public BrowserInfo getBrowserInfo() {
             return group;
     }

     public void setBrowserInfo(BrowserInfo group) {
             this.group = group;
     }

     public Group<JCZQBean> getChild() {
             return zqjcWanFas;
     }

     public void setChild(Group<JCZQBean> zqjcWanFas) {
             this.zqjcWanFas = zqjcWanFas;
     }
}

package com.jc.lottery.bean.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BetMessage {

    private String type;//彩种

    private List<List<String>> list = new ArrayList<List<String>>();
    private List<String> list0 = new ArrayList<String>();
    private List<String> list1 = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();
    private List<String> list3 = new ArrayList<String>();
    private List<String> list4 = new ArrayList<String>();
    private List<String> list5 = new ArrayList<String>();
    private List<String> list6 = new ArrayList<String>();
    private int[] no = new int[7];//数组中的数表示每个list中的球的数量
    private int[] num = new int[7];//数组中的数表示随机的上限
    private String ssq = "双色球";
    private String dlt = "大乐透";
    private String sd = "福彩3D";
    private String pls = "排列三";
    private String plw = "排列五";
    private String qxc = "七星彩";
    private String qlc = "七乐彩";
    private int sum = 0;

    private RuntimeException re = new RuntimeException("sorry, no such type");

    public String getBet() {
        initList();
        initNoAndType(type);
        initNum(type);
        return createBet(type, no, num);
    }

    private String changString(int id) {
        String id1 = "";
        if (id < 10) {
            id1 = "0" + id;
        } else {
            id1 = String.valueOf(id);
        }
        return id1;
    }

    private String createBet(String type, int[] no, int[] num) {
        type = type.toLowerCase();
        String betString = "";
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            List<String> listSort = list.get(i);
            while (listSort.size() < no[i]) {
                if (type.equals(ssq) || type.equals(dlt) || type.equals(qlc)) {
                    int ballId_01 = random.nextInt(num[i]) + 1;
                    String id1 = changString(ballId_01);
                    if (!listSort.contains(id1)) {
                        listSort.add(id1);
                    }
                } else if (type.equals(sd) || type.equals(pls) || type.equals(plw) || type.equals(qxc)) {
                    int ballId_01 = random.nextInt(num[i]);
                    String id1 = String.valueOf(ballId_01);
                    if (!listSort.contains(id1)) {
                        listSort.add(id1);
                    }
                } else {
                    throw re;
                }
            }

            Collections.sort(listSort);
            if (listSort.size() > 0) {
                if (i == sum) {
                    betString += listSort.toString();
                } else {
                    betString += listSort.toString() + "|";
                }
            }
        }

        betString = betString.replace("[", "").replace("]", "").replaceAll(", ", ",");
        if (type.equals(sd) || type.equals(pls) || type.equals(plw) || type.equals(qxc)) {
            betString = betString.replace("|", ",").replaceAll(", ", ",");
        }
        return betString;
    }

    private void initList() {
        list.add(list0);
        list.add(list1);
        list.add(list2);
        list.add(list3);
        list.add(list4);
        list.add(list5);
        list.add(list6);
    }

    private void initNoAndType(String type) {
        type = type.toLowerCase();
        if (type.equals(ssq)) {
            no[0] = 6;
            no[1] = 1;
            no[2] = 0;
            no[3] = 0;
            no[4] = 0;
            no[5] = 0;
            no[6] = 0;
            sum = 1;
        } else if (type.equals(sd) || type.equals(pls)) {
            no[0] = 1;
            no[1] = 1;
            no[2] = 1;
            no[3] = 0;
            no[4] = 0;
            no[5] = 0;
            no[6] = 0;
            sum = 2;
        } else if (type.equals(dlt)) {
            no[0] = 5;
            no[1] = 2;
            no[2] = 0;
            no[3] = 0;
            no[4] = 0;
            no[5] = 0;
            no[6] = 0;
            sum = 1;
        } else if (type.equals(plw)) {
            no[0] = 1;
            no[1] = 1;
            no[2] = 1;
            no[3] = 1;
            no[4] = 1;
            no[5] = 0;
            no[6] = 0;
            sum = 4;
        } else if (type.equals(qxc)) {
            no[0] = 1;
            no[1] = 1;
            no[2] = 1;
            no[3] = 1;
            no[4] = 1;
            no[5] = 1;
            no[6] = 1;
            sum = 6;
        } else if (type.equals(qlc)) {
            no[0] = 7;
            no[1] = 0;
            no[2] = 0;
            no[3] = 0;
            no[4] = 0;
            no[5] = 0;
            no[6] = 0;
            sum = 0;
        } else {
            throw re;
        }
    }

    private void initNum(String type) {
        type = type.toLowerCase();
        if (type.equals(ssq)) {
            num[0] = 33;
            num[1] = 16;
            num[2] = 0;
            num[3] = 0;
            num[4] = 0;
            num[5] = 0;
            num[6] = 0;
        } else if (type.equals(sd) || type.equals(pls)) {
            num[0] = 10;
            num[1] = 10;
            num[2] = 10;
            num[3] = 0;
            num[4] = 0;
            num[5] = 0;
            num[6] = 0;
        } else if (type.equals(dlt)) {
            num[0] = 35;
            num[1] = 12;
            num[2] = 0;
            num[3] = 0;
            num[4] = 0;
            num[5] = 0;
            num[6] = 0;
        } else if (type.equals(plw)) {
            num[0] = 10;
            num[1] = 10;
            num[2] = 10;
            num[3] = 10;
            num[4] = 10;
            num[5] = 0;
            num[6] = 0;
        } else if (type.equals(qxc)) {
            num[0] = 10;
            num[1] = 10;
            num[2] = 10;
            num[3] = 10;
            num[4] = 10;
            num[5] = 10;
            num[6] = 10;
        } else if (type.equals(qlc)) {
            num[0] = 30;
            num[1] = 0;
            num[2] = 0;
            num[3] = 0;
            num[4] = 0;
            num[5] = 0;
            num[6] = 0;
        } else {
            throw re;
        }
    }

    public void setType(String type) {
        this.type = type;
    }
}

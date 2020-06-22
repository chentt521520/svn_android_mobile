package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： SSQJQKJBean
 * @classDescription： 3D近期开奖
 * @author： 万
 * @createTime： 2017/12/1 15:39
 */
public class FC3DJQKJBean implements Serializable {
    private String qihao;
    private String num;
    private String xingtai;
    private String hezhi;
    private String shijihao;

    public FC3DJQKJBean() {
    }

    public FC3DJQKJBean(String qihao, String num, String xingtai, String hezhi, String shijihao) {
        this.qihao = qihao;
        this.num = num;
        this.xingtai = xingtai;
        this.hezhi = hezhi;
        this.shijihao = shijihao;
    }

    public FC3DJQKJBean(String qihao, String num, String xingtai, String hezhi) {
        this.qihao = qihao;
        this.num = num;
        this.xingtai = xingtai;
        this.hezhi = hezhi;
    }

    public String getQihao() {
        return qihao;
    }

    public void setQihao(String qihao) {
        this.qihao = qihao;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getXingtai() {
        return xingtai;
    }

    public void setXingtai(String xingtai) {
        this.xingtai = xingtai;
    }

    public String getHezhi() {
        return hezhi;
    }

    public void setHezhi(String hezhi) {
        this.hezhi = hezhi;
    }

    public String getShijihao() {
        return shijihao;
    }

    public void setShijihao(String shijihao) {
        this.shijihao = shijihao;
    }
}

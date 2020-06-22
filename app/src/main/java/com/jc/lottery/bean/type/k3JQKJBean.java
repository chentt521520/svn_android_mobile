package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： k3JQKJBean
 * @classDescription：
 * @author： 万
 * @createTime： 2017/12/1 15:39
 */
public class k3JQKJBean implements Serializable {
    private String qihao;
    private String num;
    private String hezhi;
    private String xingtai;

    public k3JQKJBean() {
    }

    public k3JQKJBean(String qihao, String num, String hezhi, String xingtai) {
        this.qihao = qihao;
        this.num = num;
        this.hezhi = hezhi;
        this.xingtai = xingtai;
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

    public String getHezhi() {
        return hezhi;
    }

    public void setHezhi(String hezhi) {
        this.hezhi = hezhi;
    }

    public String getXingtai() {
        return xingtai;
    }

    public void setXingtai(String xingtai) {
        this.xingtai = xingtai;
    }
}

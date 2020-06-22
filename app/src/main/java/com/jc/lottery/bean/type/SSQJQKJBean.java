package com.jc.lottery.bean.type;

import java.io.Serializable;

/**
 * @className： SSQJQKJBean
 * @classDescription：
 * @author： 万
 * @createTime： 2017/12/1 15:39
 */
public class SSQJQKJBean implements Serializable {
    private String qihao;
    private String num;
    private String daxiao;
    private String jiou;

    public SSQJQKJBean() {
    }

    public SSQJQKJBean(String qihao, String num, String daxiao, String jiou) {
        this.qihao = qihao;
        this.num = num;
        this.daxiao = daxiao;
        this.jiou = jiou;
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

    public String getDaxiao() {
        return daxiao;
    }

    public void setDaxiao(String daxiao) {
        this.daxiao = daxiao;
    }

    public String getJiou() {
        return jiou;
    }

    public void setJiou(String jiou) {
        this.jiou = jiou;
    }
}

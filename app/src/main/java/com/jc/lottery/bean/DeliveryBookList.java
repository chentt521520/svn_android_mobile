package com.jc.lottery.bean;

import com.jc.lottery.bean.req.pos_GetLogisticsDelivery;
import com.jc.lottery.bean.resp.DeliveryBookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 9:56
 */

public class DeliveryBookList {

    private String cartNo;
    private List<DeliveryBookBean> list = new ArrayList<DeliveryBookBean>();

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String cartNo) {
        this.cartNo = cartNo;
    }

    public List<DeliveryBookBean> getList() {
        return list;
    }

    public void setList(List<DeliveryBookBean> list) {
        this.list = list;
    }
}

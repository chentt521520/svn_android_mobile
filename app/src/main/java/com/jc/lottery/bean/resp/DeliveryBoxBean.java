package com.jc.lottery.bean.resp;

/**
 * @author lr
 * @description:
 * @date:${DATA} 10:20
 */

public class DeliveryBoxBean {

    private String cartonNo;
    private String schemeNum;
    private String sheetsNum;
    private String surplus;
    private String ticketPrice;

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getSchemeNum() {
        return schemeNum;
    }

    public void setSchemeNum(String schemeNum) {
        this.schemeNum = schemeNum;
    }

    public String getSheetsNum() {
        return sheetsNum;
    }

    public void setSheetsNum(String sheetsNum) {
        this.sheetsNum = sheetsNum;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}

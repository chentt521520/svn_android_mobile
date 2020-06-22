package com.jc.lottery.bean.req;

import com.jc.lottery.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lr
 * @description:
 * @date:${DATA} 18:05
 */

public class pos_settlementQuery {

    private String interfaceCode;
    private int requestTime;
    private String accountName;
    private String password;
    private String channel;//渠道商 0 终端 1 APP 2区块链 3移动端
    private Data data;

    public pos_settlementQuery( String accountName, String password, String channel, Data data) {
        this.interfaceCode = "query";
        this.requestTime = TimeUtils.get10IntTimeStamp();
        this.accountName = accountName;
        this.password = password;
        this.channel = channel;
        this.data = data;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private SettlementInfo settlementInfo;

        public Data(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }

        public SettlementInfo getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(SettlementInfo settlementInfo) {
            this.settlementInfo = settlementInfo;
        }
    }

    public static class SettlementInfo{
        private String gameAlias;
        private List<BookList> bookList = new ArrayList<BookList>();

        public SettlementInfo(String gameAlias, List<BookList> bookList) {
            this.gameAlias = gameAlias;
            this.bookList = bookList;
        }

        public String getGameAlias() {
            return gameAlias;
        }

        public void setGameAlias(String gameAlias) {
            this.gameAlias = gameAlias;
        }

        public List<BookList> getBookList() {
            return bookList;
        }

        public void setBookList(List<BookList> bookList) {
            this.bookList = bookList;
        }
    }

    public static class BookList{
        private String bookNum;
        private String schemeNum;

        public BookList(String bookNum, String schemeNum) {
            this.bookNum = bookNum;
            this.schemeNum = schemeNum;
        }

        public String getBookNum() {
            return bookNum;
        }

        public void setBookNum(String bookNum) {
            this.bookNum = bookNum;
        }

        public String getSchemeNum() {
            return schemeNum;
        }

        public void setSchemeNum(String schemeNum) {
            this.schemeNum = schemeNum;
        }
    }
}

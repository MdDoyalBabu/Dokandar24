package com.example.dokandar24.Common.Model;

public class CashInModel {
    int id;
    String sellerId;
    String sellerName;
    String sellerPhone;
    String time,amount,state;

    public CashInModel() {
    }

    public CashInModel(int id, String sellerId, String sellerName, String sellerPhone, String time, String amount, String state) {
        this.id = id;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.time = time;
        this.amount = amount;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public String getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }

    public String getState() {
        return state;
    }
}

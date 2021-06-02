package com.example.dokandar24.Common.Model;

public class ShopModel {
    String shopId,sellerId,shopName;
    int shopBalance,bonusBalance,sellBalance;

    public ShopModel(){

    }
    public ShopModel(String shopId, String sellerId, String shopName, int shopBalance, int bonusBalance, int sellBalance) {
        this.shopId = shopId;
        this.sellerId = sellerId;
        this.shopName = shopName;
        this.shopBalance = shopBalance;
        this.bonusBalance = bonusBalance;
        this.sellBalance = sellBalance;
    }


    public String getShopId() {
        return shopId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getShopName() {
        return shopName;
    }

    public int getShopBalance() {
        return shopBalance;
    }

    public int getBonusBalance() {
        return bonusBalance;
    }

    public int getSellBalance() {
        return sellBalance;
    }
}

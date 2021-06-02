package com.example.dokandar24.Common.Model;

import java.util.List;

public class SendMoneyListModel {
    List<SendMoneyModel> sendMoney;
    public SendMoneyListModel() {
    }
    public SendMoneyListModel(List<SendMoneyModel> sendMoney) {
        this.sendMoney = sendMoney;
    }



    public List<SendMoneyModel> getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(List<SendMoneyModel> sendMoneyList) {
        this.sendMoney = sendMoneyList;
    }
}

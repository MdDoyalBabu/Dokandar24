package com.example.dokandar24.Common.Model;

import java.util.List;

public class CashInListModel {
    List<CashInModel> cashIn;

    public CashInListModel(){

    }

    public CashInListModel(List<CashInModel> cashIn) {
        this.cashIn = cashIn;
    }

    public List<CashInModel> getCashIn() {
        return cashIn;
    }
}

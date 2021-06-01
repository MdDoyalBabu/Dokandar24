package com.example.dokandar24.Common.Model;

public class AdminAccountModel {
    int accountBalance;

    public AdminAccountModel(){

    }
    public AdminAccountModel(int accountBalance) {
        this.accountBalance = accountBalance;
    }
    public int getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }
}

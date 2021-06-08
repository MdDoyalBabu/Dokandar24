package com.example.dokandar24.Common.Model;

public class SellerModel {

    String name,profileImage,joiningDate,address,country,city,phone,password,sellerId,refer,myRefer,accountStatus;
    int cashBalance;

    public SellerModel(){

    }

    public SellerModel(String name, String joiningDate, String address, String country, String city, String phone, String password, String refer, String myRefer) {
        this.name = name;
        this.joiningDate = joiningDate;
        this.address = address;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.password = password;
        this.refer = refer;
        this.myRefer = myRefer;
    }

    public SellerModel(String name, String joiningDate, String address, String country, String city, String phone, String password, String sellerId, String refer, String myRefer, String accountStatus, int cashBalance) {
        this.name = name;
        this.profileImage=profileImage;
        this.joiningDate = joiningDate;
        this.address = address;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.password = password;
        this.sellerId = sellerId;
        this.refer = refer;
        this.myRefer = myRefer;
        this.accountStatus = accountStatus;
        this.cashBalance = cashBalance;
    }


    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getRefer() {
        return refer;
    }

    public String getMyRefer() {
        return myRefer;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public int getCashBalance() {
        return cashBalance;
    }
}

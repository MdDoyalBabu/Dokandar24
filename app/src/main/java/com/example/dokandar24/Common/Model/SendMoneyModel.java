package com.example.dokandar24.Common.Model;

public class SendMoneyModel {
    int id,amount,senderId,receiverId;
    String time,senderName,	receiverName;


    public SendMoneyModel() {
    }

    public SendMoneyModel(int id, int amount, int senderId, int receiverId, String time, String senderName, String receiverName) {
        this.id = id;
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.time = time;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getTime() {
        return time;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }
}

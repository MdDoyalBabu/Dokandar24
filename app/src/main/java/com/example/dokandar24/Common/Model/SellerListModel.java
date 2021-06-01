package com.example.dokandar24.Common.Model;

import java.util.List;

public class SellerListModel {
    List<SellerModel> seller;

    public SellerListModel(){

    }

    public SellerListModel(List<SellerModel> seller) {
        this.seller = seller;
    }

    public List<SellerModel> getSeller() {
        return seller;
    }


}

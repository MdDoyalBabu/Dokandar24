package com.example.dokandar24.Common.Model;

import java.util.List;

public class ProductListModel {
    List<ProductModel> products;
        public ProductListModel(){}
    public ProductListModel(List<ProductModel> products) {
        this.products = products;
    }

    public List<ProductModel> getProducts() {
        return products;
    }
}

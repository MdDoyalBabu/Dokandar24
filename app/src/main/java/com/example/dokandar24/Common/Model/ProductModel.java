package com.example.dokandar24.Common.Model;

import java.util.List;

public class ProductModel {

    int id,productPrice,offerPrice;
    String sellerId,shopId,category,subCategory,title,description,offer;
    List<ProductImages> images;
    public ProductModel(){}

    public ProductModel(int productPrice, String category, String subCategory, String title, String description) {
        this.productPrice = productPrice;
        this.category = category;
        this.subCategory = subCategory;
        this.title = title;
        this.description = description;
    }

    public ProductModel(int id, int productPrice, int offerPrice, String sellerId, String shopId, String category, String subCategory, String title, String description, String offer, List<ProductImages> images) {
        this.id = id;
        this.productPrice = productPrice;
        this.offerPrice = offerPrice;
        this.sellerId = sellerId;
        this.shopId = shopId;
        this.category = category;
        this.subCategory = subCategory;
        this.title = title;
        this.description = description;
        this.offer = offer;
        this.images = images;
    }

    public List<ProductImages> getImages() {
        return images;
    }

    public int getId() {
        return id;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOffer() {
        return offer;
    }
}

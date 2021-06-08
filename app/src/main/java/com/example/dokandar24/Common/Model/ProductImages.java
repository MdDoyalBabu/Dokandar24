package com.example.dokandar24.Common.Model;

public class ProductImages {
    String id,image,productId;

    public ProductImages(){}
    public ProductImages(String id, String image, String productId) {
        this.id = id;
        this.image = image;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getProductId() {
        return productId;
    }
}

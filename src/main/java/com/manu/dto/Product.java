package com.manu.dto;

public class Product {
    Integer productId;
    String productName;
    Integer availableQuantity;
    Double price;

    public String getProductQr() {
        return productQr;
    }

    public void setProductQr(String productQr) {
        this.productQr = productQr;
    }

    String productQr;
    public Product( String productName, Integer availableQuantity, Double price,String productQr) {
        this.productName = productName;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.productQr=productQr;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product() {
    }
}

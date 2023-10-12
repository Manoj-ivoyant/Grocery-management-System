package com.manu.dto;

public class PurchaseDto {
    String productQr;
    Integer quantity;

    public PurchaseDto(String productQr, Integer quantity) {
        this.productQr = productQr;
        this.quantity = quantity;
    }

    public String getProductQr() {
        return productQr;
    }

    public void setProductQr(String productQr) {
        this.productQr = productQr;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

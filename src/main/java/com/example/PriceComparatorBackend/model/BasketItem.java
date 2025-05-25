package com.example.PriceComparatorBackend.model;

public class BasketItem {
    private Product product;
    private double quantity;

    public BasketItem(Product product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }
}

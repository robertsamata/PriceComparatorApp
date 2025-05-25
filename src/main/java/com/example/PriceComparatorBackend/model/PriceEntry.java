package com.example.PriceComparatorBackend.model;

import java.time.LocalDate;

public class PriceEntry {
    private String productId;
    private String productName;
    private String brand;
    private String category;
    private String store;
    private LocalDate date;
    private double price;

    public String getProductId() {
        return productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public PriceEntry(String productId, String productName, String brand, String category, String store, LocalDate date, double price) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.store = store;
        this.date = date;
        this.price = price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
package com.example.PriceComparatorBackend.model;

public class Product {
    private String id;
    private String name;
    private String category;
    private String brand;
    private double quantity;
    private String unit;
    private double price;

    public String getId() {
        return id;
    }

    public Product(double price, String unit, double quantity, String brand, String category, String name, String id) {
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getValuePerUnit() {
        return quantity > 0 ? price / quantity : Double.MAX_VALUE;
    }
}
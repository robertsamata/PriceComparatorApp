package com.example.PriceComparatorBackend.dto;

import java.time.LocalDate;
import java.util.List;

public class BasketRequest {
    private LocalDate shoppingDate;
    private List<BasketItemRequest> items;

    // Getters and setters
    public LocalDate getShoppingDate() {
        return shoppingDate;
    }

    public void setShoppingDate(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
    }

    public List<BasketItemRequest> getItems() {
        return items;
    }

    public void setItems(List<BasketItemRequest> items) {
        this.items = items;
    }
}
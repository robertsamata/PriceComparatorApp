package com.example.PriceComparatorBackend.dto;

import com.example.PriceComparatorBackend.model.BasketItem;

import java.util.List;

public class BasketResponse {
    private List<BasketItem> discounted;
    private List<BasketItem> nonDiscounted;

    public BasketResponse(List<BasketItem> discounted, List<BasketItem> nonDiscounted) {
        this.discounted = discounted;
        this.nonDiscounted = nonDiscounted;
    }

    public List<BasketItem> getDiscounted() {
        return discounted;
    }

    public void setDiscounted(List<BasketItem> discounted) {
        this.discounted = discounted;
    }

    public List<BasketItem> getNonDiscounted() {
        return nonDiscounted;
    }

    public void setNonDiscounted(List<BasketItem> nonDiscounted) {
        this.nonDiscounted = nonDiscounted;
    }
}

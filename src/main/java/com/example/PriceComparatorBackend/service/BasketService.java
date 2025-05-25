package com.example.PriceComparatorBackend.service;

import com.example.PriceComparatorBackend.model.BasketItem;
import com.example.PriceComparatorBackend.model.Discount;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasketService {
    public Map<String, List<BasketItem>> splitBasketByDiscount(List<BasketItem> basket, List<Discount> discounts, LocalDate date) {
        List<BasketItem> discountedItems = new ArrayList<>();
        List<BasketItem> nonDiscountedItems = new ArrayList<>();

        for (BasketItem item : basket) {
            boolean hasDiscount = discounts.stream()
                    .anyMatch(d -> d.getProductId().equals(item.getProduct().getId())
                            && !date.isBefore(d.getFromDate())
                            && !date.isAfter(d.getToDate()));

            if (hasDiscount) {
                discountedItems.add(item);
            } else {
                nonDiscountedItems.add(item);
            }
        }

        Map<String, List<BasketItem>> result = new HashMap<>();
        result.put("discounted", discountedItems);
        result.put("nonDiscounted", nonDiscountedItems);

        return result;
    }
}

package com.example.PriceComparatorBackend.controller;

import com.example.PriceComparatorBackend.dto.BasketRequest;
import com.example.PriceComparatorBackend.dto.BasketResponse;
import com.example.PriceComparatorBackend.model.BasketItem;
import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.model.Product;
import com.example.PriceComparatorBackend.service.BasketService;
import com.example.PriceComparatorBackend.util.CsvReader;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;
    private final CsvReader csvReader; // ✅

    public BasketController(BasketService basketService, CsvReader csvReader) { // ✅
        this.basketService = basketService;
        this.csvReader = csvReader;
    }

    @PostMapping("/split")
    public BasketResponse splitBasket(@RequestBody BasketRequest request) {
        LocalDate date = request.getShoppingDate() != null ? request.getShoppingDate() : LocalDate.now();

        List<Product> allProducts = csvReader.loadProducts("lidl_2025-05-08.csv");
        List<Discount> discounts = csvReader.loadDiscounts("lidl_discounts_2025-05-08.csv");


        List<BasketItem> basket = request.getItems().stream()
                .map(reqItem -> {
                    Product p = allProducts.stream()
                            .filter(prod -> prod.getName().equalsIgnoreCase(reqItem.getProductName()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Product not found: " + reqItem.getProductName()));
                    return new BasketItem(p, reqItem.getQuantity());
                })
                .collect(Collectors.toList());

        Map<String, List<BasketItem>> split = basketService.splitBasketByDiscount(basket, discounts, date);
        return new BasketResponse(split.get("discounted"), split.get("nonDiscounted"));
    }
}

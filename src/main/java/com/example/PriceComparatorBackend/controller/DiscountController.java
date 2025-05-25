package com.example.PriceComparatorBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.service.DiscountService;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public List<Discount> getDiscounts() {
        return discountService.getAllDiscounts();
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveDiscounts(@RequestBody Discount discount) {
        discountService.saveDiscounts(discount);
        return ResponseEntity.ok("Discounts saved successfully.");
    }
}

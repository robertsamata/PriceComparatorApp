package com.example.PriceComparatorBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PriceComparatorBackend.model.PriceAlert;
import com.example.PriceComparatorBackend.service.ProductService;

@RestController
@RequestMapping("/alerts")
public class PriceAlertController {
    private final ProductService productService;

    public PriceAlertController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createAlert(@RequestBody PriceAlert alert) {
        productService.addPriceAlert(alert);
        return ResponseEntity.ok("Price alert added.");
    }

    @GetMapping("/triggered")
    public List<PriceAlert> getTriggeredAlerts() {
        return productService.checkPriceAlerts();
    }
}

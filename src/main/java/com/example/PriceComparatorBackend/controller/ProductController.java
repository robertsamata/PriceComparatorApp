package com.example.PriceComparatorBackend.controller;

import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.model.Product;
import com.example.PriceComparatorBackend.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        service.addProduct(product);
        return ResponseEntity.ok("Products saved successfully.");

    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        boolean deleted = service.deleteProduct(productId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/discounts/top")
    public List<Product> topDiscounts() {
        return service.getTopDiscountedProducts();
    }

    @GetMapping("/discounts/new")
    public List<Discount> newDiscounts() {
        return service.getNewDiscounts();
    }

    @GetMapping("/{productName}/recommendations")
    public ResponseEntity<Map<String, Object>> getRecommendations(@PathVariable String productName) {
        Product originalProduct = service.getProductByName(productName);
        if (originalProduct == null) {
            return ResponseEntity.notFound().build();
        }

        List<Product> recommendedProducts = service.getSubstituteRecommendationsByName(productName);

        Map<String, Object> response = new HashMap<>();
        response.put("originalProduct", originalProduct);
        response.put("recommendations", recommendedProducts.stream().map(p -> {
            double valuePerUnit = service.getValuePerUnit(p);
            double discountedPrice = service.getDiscountedPrice(p);
            Map<String, Object> map = new HashMap<>();
            map.put("name", p.getName());
            map.put("brand", p.getBrand());
            map.put("category", p.getCategory());
            map.put("unit", p.getUnit());
            map.put("quantity", p.getQuantity());
            map.put("originalPrice", p.getPrice());
            map.put("discountedPrice", discountedPrice);
            map.put("valuePerUnit", String.format("%.2f per %s", valuePerUnit, p.getUnit()));
            return map;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
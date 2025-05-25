package com.example.PriceComparatorBackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.model.PriceAlert;
import com.example.PriceComparatorBackend.model.Product;
import com.example.PriceComparatorBackend.util.CsvReader;

@Service
public class DiscountService {
    private final CsvReader csvReader;

    private List<Product> products;
    private List<Discount> discounts;
    private static final String productsCsv = "lidl_2025-05-08.csv";
    private static final String discountsCsv = "lidl_discounts_2025-05-08.csv";

    public DiscountService(CsvReader csvReader) {
        this.csvReader = csvReader;
        this.products = csvReader.loadProducts(productsCsv);
        this.discounts = csvReader.loadDiscounts(discountsCsv);
    }

    public List<Discount> getAllDiscounts() {
        return new ArrayList<>(discounts);
    }

    public List<Discount> getNewDiscounts() {
        LocalDate now = LocalDate.now();
        return discounts.stream()
                .filter(d -> d.getFromDate().isEqual(now) || d.getFromDate().isAfter(now.minusDays(1)))
                .toList();
    }

    public void saveDiscounts(Discount discount) {
        discounts.add(discount);
        csvReader.writeDiscountsToCsv(csvReader.loadProducts(productsCsv),discounts, discountsCsv);
    }
}

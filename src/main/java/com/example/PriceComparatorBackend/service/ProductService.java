package com.example.PriceComparatorBackend.service;

import com.example.PriceComparatorBackend.model.Discount;
import com.example.PriceComparatorBackend.model.PriceAlert;
import com.example.PriceComparatorBackend.model.Product;
import com.example.PriceComparatorBackend.util.CsvReader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final CsvReader csvReader;
    private List<Product> products;
    private List<Discount> discounts;
    private List<PriceAlert> alerts = new ArrayList<>();
    private static final String productsCsv = "lidl_2025-05-08.csv";
    private static final String discountsCsv = "lidl_discounts_2025-05-08.csv";


    public ProductService(CsvReader csvReader) {
        this.csvReader = csvReader;
        this.products = csvReader.loadProducts(productsCsv);
        this.discounts = csvReader.loadDiscounts(discountsCsv);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Product product) {
        products.add(product);
        csvReader.writeProductsToCsv(products, productsCsv);
    }

    public boolean deleteProduct(String productId) {
        boolean removed = products.removeIf(p -> p.getId().equalsIgnoreCase(productId));
        if (removed) {
            csvReader.writeProductsToCsv(products, productsCsv);
        }
        return removed;
    }

    public List<Product> getTopDiscountedProducts() {
        return discounts.stream()
                .sorted(Comparator.comparingInt(Discount::getPercentage).reversed())
                .limit(5)
                .map(d -> products.stream()
                        .filter(p -> p.getId().equals(d.getProductId()))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Discount> getNewDiscounts() {
        LocalDate now = LocalDate.now();
        return discounts.stream()
                .filter(d -> d.getFromDate().isEqual(now) || d.getFromDate().isAfter(now.minusDays(1)))
                .collect(Collectors.toList());
    }

    public double getDiscountedPrice(Product product) {
        this.discounts = csvReader.loadDiscounts(discountsCsv);

        LocalDate today = LocalDate.now();
        return discounts.stream()
                .filter(d -> d.getProductId().equalsIgnoreCase(product.getName()) &&
                        !today.isBefore(d.getFromDate()) &&
                        !today.isAfter(d.getToDate()))
                .findFirst()
                .map(d -> product.getPrice() * (1 - d.getPercentage() / 100.0))
                .orElse(product.getPrice());
    }


    public double getValuePerUnit(Product product) {
        double price = getDiscountedPrice(product);
        return product.getQuantity() > 0 ? price / product.getQuantity() : Double.MAX_VALUE;
    }

    public List<Product> getSubstituteRecommendationsByName(String productName) {
        Product original = products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return products.stream()
                .filter(p -> !p.getName().equalsIgnoreCase(productName) &&
                        (p.getCategory().equalsIgnoreCase(original.getCategory()) ||
                         p.getBrand().equalsIgnoreCase(original.getBrand())))
                .sorted(Comparator.comparingDouble(this::getValuePerUnit))
                .collect(Collectors.toList());
    }

    public Product getProductByName(String productName) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);
   }

   public void addPriceAlert(PriceAlert alert) {
        alerts.add(alert);
    }

    public List<PriceAlert> checkPriceAlerts() {
        List<PriceAlert> triggeredAlerts = new ArrayList<>();
        for (PriceAlert alert : alerts) {
            Product product = getProductByName(alert.getProductName());
            if (product != null) {
                double currentPrice = getDiscountedPrice(product);
                if (currentPrice <= alert.getTargetPrice()) {
                    triggeredAlerts.add(alert);
                }
            }
        }
        return triggeredAlerts;
    }
}
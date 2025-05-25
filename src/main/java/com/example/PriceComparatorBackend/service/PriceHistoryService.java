package com.example.PriceComparatorBackend.service;

import com.example.PriceComparatorBackend.model.PriceEntry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceHistoryService {
    private final PriceHistoryReader reader;

    public PriceHistoryService(PriceHistoryReader reader) {
        this.reader = reader;
    }

    public List<PriceEntry> getFilteredHistory(String productId, String store, String category, String brand) {
        List<PriceEntry> all = reader.loadPriceHistory("price_history.csv");
        return all.stream()
                .filter(e -> (productId == null || e.getProductId().equals(productId)))
                .filter(e -> (store == null || e.getStore().equalsIgnoreCase(store)))
                .filter(e -> (category == null || e.getCategory().equalsIgnoreCase(category)))
                .filter(e -> (brand == null || e.getBrand().equalsIgnoreCase(brand)))
                .collect(Collectors.toList());
    }
}

package com.example.PriceComparatorBackend.controller;

import com.example.PriceComparatorBackend.model.PriceEntry;
import com.example.PriceComparatorBackend.service.PriceHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceHistoryController {

    private final PriceHistoryService historyService;

    public PriceHistoryController(PriceHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public List<PriceEntry> getPriceHistory(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String store,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand

    ) {
        return historyService.getFilteredHistory(productId, store, category, brand);
    }
}

package com.example.PriceComparatorBackend.service;

import com.example.PriceComparatorBackend.model.PriceEntry;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PriceHistoryReader {
    public List<PriceEntry> loadPriceHistory(String filename) {
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("data/" + filename))))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<PriceEntry> entries = new ArrayList<>();
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                if (line.length < 4) continue;

                entries.add(new PriceEntry(
                        line[0], // productId
                        line[1], // productName
                        line[2], // brand
                        line[3], // category
                        line[4], // store
                        LocalDate.parse(line[5]), // date
                        Double.parseDouble(line[6].replace(',', '.')) // price
                ));
            }
            return entries;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

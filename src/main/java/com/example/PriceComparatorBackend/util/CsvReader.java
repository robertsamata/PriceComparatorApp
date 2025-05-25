package com.example.PriceComparatorBackend.util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;


import com.example.PriceComparatorBackend.model.Discount;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.example.PriceComparatorBackend.model.Product;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import org.springframework.stereotype.Component;

@Component
public class CsvReader {
    public List<Product> loadProducts(String filename) {
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("data/" + filename))))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<Product> products = new ArrayList<>();
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                if (line.length < 7) {
                    System.out.println(" error " + Arrays.toString(line));
                    continue;
                }

                String id = line[0];
                String name = line[1];
                String category = line[2];
                String brand = line[3];
                double quantity = Double.parseDouble(line[4].replace(',', '.'));
                String unit = line[5];
                double price = Double.parseDouble(line[6].replace(',', '.'));

                products.add(new Product(price, unit, quantity, brand, category, name, id));
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public List<Discount> loadDiscounts(String filename) {
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("data/" + filename)))
        )
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<Discount> discounts = new ArrayList<>();
            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                if (line.length < 9) {
                    System.out.println(" error " + Arrays.toString(line));
                    continue;
                }

                String productId = line[0];
                LocalDate fromDate = LocalDate.parse(line[6]);
                LocalDate toDate = LocalDate.parse(line[7]);
                int percentage = Integer.parseInt(line[8]);

                discounts.add(new Discount(productId, fromDate, toDate, percentage));
            }

            return discounts;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void writeProductsToCsv(List<Product> products, String filename) {
        File file = new File("PriceComparatorBackend/src/main/resources/data/" + filename);
        try (CSVWriter writer = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)){
            writer.writeNext(new String[]{"product_id", "product_name", "product_category", "brand", "package_quantity", "package_unit", "price", "currency"});
            for (Product p : products) {
                String quantityStr;
                if (p.getQuantity() == Math.floor(p.getQuantity())) {
                    quantityStr = String.format("%.0f", p.getQuantity());
                } else {
                    quantityStr = String.valueOf(p.getQuantity());
                }

                String priceStr = String.format("%.2f", p.getPrice());

                writer.writeNext(new String[]{
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getBrand(),
                    quantityStr,
                    p.getUnit(),
                    priceStr,
                    "RON"
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDiscountsToCsv(List<Product> products, List<Discount> discounts, String filename) {

        File file = new File("PriceComparatorBackend/src/main/resources/data/" + filename);
        try (CSVWriter writer = new CSVWriter(
                new FileWriter(file),
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

            writer.writeNext(new String[]{
                "product_id", "product_name", "brand", "package_quantity", "package_unit",
                "product_category", "from_date", "to_date", "percentage_of_discount"
            });

            for (Discount d : discounts) {
                Optional<Product> productOpt = products.stream()
                        .filter(p -> p.getId().equals(d.getProductId()))
                        .findFirst();

                if (productOpt.isEmpty()) {
                    System.out.println("Warning: Product not found for discount: " + d.getProductId());
                    continue;
                }

                Product p = productOpt.get();

                String quantityStr;
                if (p.getQuantity() == Math.floor(p.getQuantity())) {
                    quantityStr = String.format("%.0f", p.getQuantity());
                } else {
                    quantityStr = String.valueOf(p.getQuantity());
                }

                writer.writeNext(new String[]{
                    d.getProductId(),
                    p.getName(),
                    p.getBrand(),
                    quantityStr,
                    p.getUnit(),
                    p.getCategory(),
                    d.getFromDate().toString(),
                    d.getToDate().toString(),
                    String.valueOf(d.getPercentage())
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


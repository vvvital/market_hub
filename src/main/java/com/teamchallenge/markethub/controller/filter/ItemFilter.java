package com.teamchallenge.markethub.controller.filter;

import com.teamchallenge.markethub.dto.item.detail.ItemDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ItemFilter {
    public static List<ItemDetails> toFilter(List<ItemDetails> list, ItemRequestParams params) {
        return list.stream()
                .filter(priceInRange(params.priceFrom(), params.priceTo()))
                .filter(checkAvailable(params.available()))
                .filter(checkBrand(params.brand()))
                .toList();
    }

    private static Predicate<? super ItemDetails> checkBrand(List<String> brands) {
        return (item) -> {
            for (String brand : brands) {
                if (Objects.equals(brand, "empty") || Objects.equals(brand, item.getBrand())) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Predicate<? super ItemDetails> checkAvailable(String available) {
        return (item) -> Objects.equals(available, "empty") || Objects.equals(available, String.valueOf(item.isAvailable()));
    }

    private static Predicate<? super ItemDetails> priceInRange(double priceFrom, double priceTo) {
        return (item) -> isPriceGreaterThan(priceFrom, item) && isPriceLessThan(priceTo, item);
    }

    private static boolean isPriceGreaterThan(double priceFrom, ItemDetails item) {
        return item.getPrice().compareTo(BigDecimal.valueOf(priceFrom)) > 0;
    }

    private static boolean isPriceLessThan(double priceTo, ItemDetails item) {
        return item.getPrice().compareTo(BigDecimal.valueOf(priceTo)) < 0;
    }
}

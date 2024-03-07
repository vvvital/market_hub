package com.teamchallenge.markethub.controller.filter;

import com.teamchallenge.markethub.dto.item.ItemResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.DEFAULT;


public final class ItemFilter {

    public static List<ItemResponse> toFilter(List<ItemResponse> list, ItemsFilterParams params) {
        return list.stream()
                .filter(priceInRange(params.priceFrom(), params.priceTo()))
                .filter(checkAvailable(params.available()))
                .filter(checkBrand(params.brands()))
                .toList();
    }

    private static Predicate<? super ItemResponse> checkBrand(List<String> brands) {
        return (item) -> {
            for (String brand : brands) {
                if (Objects.equals(brand, DEFAULT) || Objects.equals(brand, item.getBrand())) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Predicate<? super ItemResponse> checkAvailable(String available) {
        return (item) -> Objects.equals(available, FilterDefaultValues.DEFAULT) || Objects.equals(available, String.valueOf(item.isAvailable()));
    }

    private static Predicate<? super ItemResponse> priceInRange(double priceFrom, double priceTo) {
        return (item) -> isPriceGreaterThan(priceFrom, item) && isPriceLessThan(priceTo, item);
    }

    private static boolean isPriceGreaterThan(double priceFrom, ItemResponse item) {
        return item.getPrice().compareTo(BigDecimal.valueOf(priceFrom)) > 0;
    }

    private static boolean isPriceLessThan(double priceTo, ItemResponse item) {
        return item.getPrice().compareTo(BigDecimal.valueOf(priceTo)) < 0;
    }
}

package com.teamchallenge.markethub.controller.filter;

import com.teamchallenge.markethub.model.Item;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.NOT_SPECIFIED;

public final class Filter {

    private Filter() {
    }


    public static Specification<Item> doFilter(ItemsFilterParams filterParams, String attributeName) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>(4);

            filterById(filterParams, attributeName, root, builder, predicates);
            filterByPrice(filterParams, root, builder, predicates);
            filterByAvailable(filterParams, root, builder, predicates);
            filterByBrands(filterParams, root, predicates);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void filterByBrands(ItemsFilterParams filterParams, Root<Item> root, List<Predicate> predicates) {
        if (!Objects.equals(filterParams.brands().get(0), NOT_SPECIFIED)) {
            List<String> brands = filterParams.brands();
            predicates.add(root.get("brand").in((brands)));
        }
    }

    private static void filterByAvailable(ItemsFilterParams filterParams, Root<Item> root, CriteriaBuilder builder, List<Predicate> predicates) {
        if (!Objects.equals(filterParams.available(), NOT_SPECIFIED)) {
            boolean val = Objects.equals(filterParams.available(), "true");
            predicates.add(builder.equal(root.get("available"), val));
        }
    }

    private static void filterByPrice(ItemsFilterParams filterParams, Root<Item> root, CriteriaBuilder builder, List<Predicate> predicates) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("price"), filterParams.priceFrom()));
        predicates.add(builder.lessThanOrEqualTo(root.get("price"), filterParams.priceTo()));
    }

    private static void filterById(ItemsFilterParams filterParams, String attributeName, Root<Item> root, CriteriaBuilder builder, List<Predicate> predicates) {
        predicates.add(builder.equal(root.join(attributeName).get("id"), filterParams.id()));
    }
}

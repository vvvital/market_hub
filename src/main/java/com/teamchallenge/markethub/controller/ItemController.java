package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.controller.filter.ItemFilter;
import com.teamchallenge.markethub.controller.filter.ItemsFilterParams;
import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.dto.item.ItemsResponse;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.*;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub/goods")
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/categories/{category_id}")
    public ResponseEntity<ItemsResponse> getItemsByCategoryId(
            @PathVariable(name = "category_id") long categoryId, Pageable pageable,
            @RequestParam(name = "price_from", required = false, defaultValue = MIN_VALUE) double priceFrom,
            @RequestParam(name = "price_to", required = false, defaultValue = MAX_VALUE) double priceTo,
            @RequestParam(name = "available", required = false, defaultValue = DEFAULT) String available,
            @RequestParam(name = "brands", required = false, defaultValue = DEFAULT) List<String> brands) {

        List<ItemResponse> filteredItemList = getFilter(itemService::getAllItemByCategoryId,
                categoryId, pageable, getRequestParams(priceFrom, priceTo, available, brands));
        int size = getCountItemsById(itemService::getCountItemsByCategoryId, categoryId);

        return ResponseEntity.ok(new ItemsResponse(size, filteredItemList));
    }

    @GetMapping("/sub-categories/{sub_category_id}")
    public ResponseEntity<ItemsResponse> getItemsBySubCategoryId(
            @PathVariable(name = "sub_category_id") long subCategoryId, Pageable pageable,
            @RequestParam(name = "price_from", required = false, defaultValue = MIN_VALUE) double priceFrom,
            @RequestParam(name = "price_to", required = false, defaultValue = MAX_VALUE) double priceTo,
            @RequestParam(name = "available", required = false, defaultValue = DEFAULT) String available,
            @RequestParam(name = "brands", required = false, defaultValue = DEFAULT) List<String> brands) {

        List<ItemResponse> filteredItemList = getFilter(itemService::getAllItemBySubCategoryId,
                subCategoryId, pageable, getRequestParams(priceFrom, priceTo, available, brands));
        int size = getCountItemsById(itemService::getCountItemsBySubCategoryId, subCategoryId);

        return ResponseEntity.ok(new ItemsResponse(size, filteredItemList));
    }

    private static ItemsFilterParams getRequestParams(double priceFrom, double priceTo, String available, List<String> brand) {
        return new ItemsFilterParams(priceFrom, priceTo, available, brand);
    }

    private int getCountItemsById(Function<Long, Integer> function, long id) {
        return function.apply(id);
    }

    private List<ItemResponse> getFilter(BiFunction<Long, Pageable, List<ItemResponse>> function, long id, Pageable pageable, ItemsFilterParams params) {
        List<ItemResponse> list = function.apply(id, pageable);
        return ItemFilter.toFilter(list, params);
    }

    @GetMapping("/{item_id}")
    @Operation(summary = "Returns item by Id")
    public ResponseEntity<ItemCardResponse> getItem(@PathVariable(name = "item_id") long itemId) {
        return ResponseEntity.ok(itemService.getItemCardById(itemId));
    }

    @GetMapping("/top-seller")
    @Operation(summary = "Returns the top 4 best selling items")
    public ResponseEntity<ItemsResponse> getTopSellerList() {
//        ItemResponse item = itemService.getItemById(100);
//        ItemResponse item2 = itemService.getItemById(128);
//        ItemResponse item3 = itemService.getItemById(153);
//        ItemResponse item4 = itemService.getItemById(178);
//        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(itemService.getTopSellerList());
    }

    @GetMapping("/shares")
    @Operation(summary = "Returns top 4 items are most in stock")
    public ResponseEntity<ItemsResponse> getShares() {
//        ItemResponse item = itemService.getItemById(203);
//        ItemResponse item2 = itemService.getItemById(228);
//        ItemResponse item3 = itemService.getItemById(253);
//        ItemResponse item4 = itemService.getItemById(278);
//        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(itemService.shares());
    }
}

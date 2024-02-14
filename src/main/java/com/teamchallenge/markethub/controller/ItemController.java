package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.controller.filter.ItemFilter;
import com.teamchallenge.markethub.controller.filter.ItemRequestParams;
import com.teamchallenge.markethub.dto.item.ItemDetailsResponse;
import com.teamchallenge.markethub.dto.item.detail.ItemDetails;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;
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
    private static final String MIN_VALUE = "0";
    private static final String MAX_VALUE = "1000000";
    private static final String EMPTY = "empty";

    private final ItemServiceImpl itemService;

    @GetMapping("/categories/{category_id}")
    public ResponseEntity<ItemResponse> getItemsByCategoryId(
            @PathVariable(name = "category_id") long categoryId, Pageable pageable,
            @RequestParam(name = "price_from", required = false, defaultValue = MIN_VALUE) double priceFrom,
            @RequestParam(name = "price_to", required = false, defaultValue = MAX_VALUE) double priceTo,
            @RequestParam(name = "available", required = false, defaultValue = EMPTY) String available,
            @RequestParam(name = "brand", required = false, defaultValue = EMPTY) String brand) {
        List<ItemDetails> filteredItemList = getFilter(itemService::getAllItemByCategoryId,
                categoryId, pageable, getRequestParams(priceFrom, priceTo, available, brand));
        int size = getCountItemsById(itemService::getCountItemsByCategoryId, categoryId);
        return ResponseEntity.ok(new ItemResponse(size, filteredItemList));
    }

    @GetMapping("/sub-categories/{sub_category_id}")
    public ResponseEntity<ItemResponse> getItemsBySubCategoryId(
            @PathVariable(name = "sub_category_id") long subCategoryId, Pageable pageable,
            @RequestParam(name = "price_from", required = false, defaultValue = MIN_VALUE) double priceFrom,
            @RequestParam(name = "price_to", required = false, defaultValue = MAX_VALUE) double priceTo,
            @RequestParam(name = "available", required = false, defaultValue = EMPTY) String available,
            @RequestParam(name = "brand", required = false, defaultValue = EMPTY) String brand) {
        List<ItemDetails> filteredItemList = getFilter(itemService::getAllItemBySubCategoryId,
                subCategoryId, pageable, getRequestParams(priceFrom, priceTo, available, brand));
        int size = getCountItemsById(itemService::getCountItemsBySubCategoryId, subCategoryId);
        return ResponseEntity.ok(new ItemResponse(size, filteredItemList));
    }

    private static ItemRequestParams getRequestParams(double priceFrom, double priceTo, String available, String brand) {
        return new ItemRequestParams(priceFrom, priceTo, available, brand);
    }

    private int getCountItemsById(Function<Long, Integer> function, long id) {
        return function.apply(id);
    }
    private List<ItemDetails> getFilter(BiFunction<Long,Pageable, List<ItemDetails>> function, long id, Pageable pageable, ItemRequestParams params) {
        List<ItemDetails> list = function.apply(id, pageable);
        return ItemFilter.toFilter(list, params);
    }

    @GetMapping("/{item_id}")
    public ResponseEntity<ItemDetailsResponse> getItem(@PathVariable(name = "item_id") long itemId) throws ItemNotFoundException {
        return ResponseEntity.ok(itemService.findItemById(itemId));
    }

    @GetMapping("/top-seller")
    public ResponseEntity<List<ItemDetails>> getTopSellerList() {
        ItemDetails item = itemService.findItem(100);
        ItemDetails item2 = itemService.findItem(128);
        ItemDetails item3 = itemService.findItem(153);
        ItemDetails item4 = itemService.findItem(178);

        List<ItemDetails> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/shares")
    public ResponseEntity<List<ItemDetails>> getShares() {
        ItemDetails item = itemService.findItem(203);
        ItemDetails item2 = itemService.findItem(228);
        ItemDetails item3 = itemService.findItem(253);
        ItemDetails item4 = itemService.findItem(278);

        List<ItemDetails> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }
}

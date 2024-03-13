package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.controller.filter.ItemsFilterParams;
import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.dto.item.ItemsResponse;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.*;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
            @RequestParam(name = "available", required = false, defaultValue = NOT_SPECIFIED) String available,
            @RequestParam(name = "brands", required = false, defaultValue = NOT_SPECIFIED) List<String> brands) {

        ItemsFilterParams itemsFilterParams = getRequestParams(categoryId, priceFrom, priceTo, available, brands);
        List<ItemResponse> filteredItemList = itemService.getAllItemByCategoryId(itemsFilterParams, pageable);
        int size = total(itemService::getCountItemsByCategoryId, categoryId);

        return ResponseEntity.ok(new ItemsResponse(size, filteredItemList));
    }

    @GetMapping("/sub-categories/{sub_category_id}")
    public ResponseEntity<ItemsResponse> getItemsBySubCategoryId(
            @PathVariable(name = "sub_category_id") long subCategoryId, Pageable pageable,
            @RequestParam(name = "price_from", required = false, defaultValue = MIN_VALUE) double priceFrom,
            @RequestParam(name = "price_to", required = false, defaultValue = MAX_VALUE) double priceTo,
            @RequestParam(name = "available", required = false, defaultValue = NOT_SPECIFIED) String available,
            @RequestParam(name = "brands", required = false, defaultValue = NOT_SPECIFIED) List<String> brands) {

        ItemsFilterParams itemsFilterParams = getRequestParams(subCategoryId, priceFrom, priceTo, available, brands);
        List<ItemResponse> filteredItemList = itemService.getAllItemBySubCategoryId(itemsFilterParams, pageable);
        int total = total(itemService::getCountItemsBySubCategoryId, subCategoryId);

        return ResponseEntity.ok(new ItemsResponse(total, filteredItemList));
    }

    private static ItemsFilterParams getRequestParams(long id, double priceFrom, double priceTo, String available, List<String> brand) {
        return new ItemsFilterParams(id, priceFrom, priceTo, available, brand);
    }

    private int total(Function<Long, Integer> function, long id) {
        return function.apply(id);
    }


    @GetMapping("/{item_id}")
    public ResponseEntity<ItemCardResponse> getItem(@PathVariable(name = "item_id") long itemId) {
        return ResponseEntity.ok(itemService.getItemCardById(itemId));
    }

    @GetMapping("/top-seller")
    public ResponseEntity<List<ItemResponse>> getTopSellerList() {
        ItemResponse item = itemService.getItemById(100);
        ItemResponse item2 = itemService.getItemById(128);
        ItemResponse item3 = itemService.getItemById(153);
        ItemResponse item4 = itemService.getItemById(178);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/shares")
    public ResponseEntity<List<ItemResponse>> getShares() {
        ItemResponse item = itemService.getItemById(203);
        ItemResponse item2 = itemService.getItemById(228);
        ItemResponse item3 = itemService.getItemById(253);
        ItemResponse item4 = itemService.getItemById(278);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }
}

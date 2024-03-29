package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.controller.filter.ItemsFilterParams;
import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.dto.item.ItemsResponse;
import com.teamchallenge.markethub.dto.item.NewItemRequest;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.*;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Returns item by Id")
    public ResponseEntity<ItemCardResponse> getItem(@PathVariable(name = "item_id") long itemId) {
        return ResponseEntity.ok(itemService.getItemCardById(itemId));
    }

    @GetMapping("/top-seller")
    @Operation(summary = "Returns the top 4 best selling items")
    public ResponseEntity<ItemsResponse> getTopSellerList() {
        return ResponseEntity.status(200).body(itemService.getTopSellerList());
    }

    @GetMapping("/shares")
    @Operation(summary = "Returns top 4 items are most in stock")
    public ResponseEntity<ItemsResponse> getShares() {
        return ResponseEntity.status(200).body(itemService.shares());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createNewItem(@RequestBody NewItemRequest newItemRequest) {
        System.out.println("name: " + newItemRequest.getName());
        System.out.println("buffer: " + newItemRequest.getPhotos());

        return ResponseEntity.noContent().build();
    }

}

package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.item.ItemDetailsResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub/goods")
public class ItemController {

    private final ItemServiceImpl itemService;


    @GetMapping("/categories/{category_id}")
    public ResponseEntity<List<ItemResponse>> getItemsByCategoryId(
            @PathVariable(name = "category_id") long categoryId,
            Pageable pageable) {

        List<ItemResponse> itemList = itemService.getAllItemByCategoryId(categoryId, pageable);
        return ResponseEntity.status(200).body(itemList);
    }

    @GetMapping("/sub-categories/{sub_category_id}")
    public ResponseEntity<List<ItemResponse>> getItemsBySubCategoryId(
            @PathVariable(name = "sub_category_id") long subCategoryId,
            Pageable pageable) {

        List<ItemResponse> itemList = itemService.getAllItemBySubCategoryId(subCategoryId, pageable);
        return ResponseEntity.status(200).body(itemList);
    }

    @GetMapping("/{item_id}")
    public ResponseEntity<ItemDetailsResponse> getItem(@PathVariable(name = "item_id") long itemId) throws ItemNotFoundException {
        return ResponseEntity.status(200).body(itemService.findItemById(itemId));
    }

    @GetMapping("/top-seller")
    public ResponseEntity<List<ItemResponse>> getTopSellerList() {
        ItemResponse item = itemService.findItem(100);
        ItemResponse item2 = itemService.findItem(128);
        ItemResponse item3 = itemService.findItem(153);
        ItemResponse item4 = itemService.findItem(178);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/shares")
    public ResponseEntity<List<ItemResponse>> getShares() {
        ItemResponse item = itemService.findItem(203);
        ItemResponse item2 = itemService.findItem(228);
        ItemResponse item3 = itemService.findItem(253);
        ItemResponse item4 = itemService.findItem(278);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

}

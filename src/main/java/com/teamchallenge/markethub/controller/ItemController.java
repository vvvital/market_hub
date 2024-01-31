package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.service.SubCategoryService;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub/goods")
public class ItemController {

    private final ItemServiceImpl itemService;
    private final SubCategoryService subCategoryService;

    @GetMapping("/{category_id}")
    public ResponseEntity<List<ItemResponse>> getAllItemSameCategory(@PathVariable(name = "category_id") long categoryId) {
        List<ItemResponse> itemList = itemService.getAllItemByCategoryId(categoryId);
        return ResponseEntity.status(200).body(itemList);
    }

    @GetMapping("/{category_id}/{sub_category_id}")
    public ResponseEntity<List<ItemResponse>> getAllItemSameSubCategory(@PathVariable(name = "category_id") long categoryId, @PathVariable(name = "sub_category_id") long subCategoryId)  {
        List<Long> subCategoryIds  = subCategoryService.findAllSubCategoriesByParent(categoryId).stream()
                .map(SubCategoryResponse::id)
                .toList();

        if (subCategoryIds.contains(subCategoryId)) {
            List<ItemResponse> itemList = itemService.getAllItemBySubCategoryId(subCategoryId);
            return ResponseEntity.status(200).body(itemList);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/top-seller")
    public ResponseEntity<List<ItemResponse>> getTopSellerList() throws ItemNotFoundException {
        ItemResponse item = itemService.findItemById(100);
        ItemResponse item2 = itemService.findItemById(128);
        ItemResponse item3 = itemService.findItemById(153);
        ItemResponse item4 = itemService.findItemById(178);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/shares")
    public ResponseEntity<List<ItemResponse>> getShares() throws ItemNotFoundException {
        ItemResponse item = itemService.findItemById(203);
        ItemResponse item2 = itemService.findItemById(228);
        ItemResponse item3 = itemService.findItemById(253);
        ItemResponse item4 = itemService.findItemById(278);

        List<ItemResponse> list = Arrays.asList(item, item2, item3, item4);
        return ResponseEntity.status(200).body(list);
    }

}

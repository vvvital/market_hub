package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.controller.filter.ItemsFilterParams;
import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.dto.item.ItemsResponse;
import com.teamchallenge.markethub.dto.item.NewItemRequest;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.Photo;
import com.teamchallenge.markethub.service.CategoryService;
import com.teamchallenge.markethub.service.impl.ItemServiceImpl;
import com.teamchallenge.markethub.service.impl.PhotoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.*;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub/goods")
public class ItemController {

    private final ItemServiceImpl itemService;
    private final PhotoServiceImpl photoService;
    private final CategoryService categoryService;

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

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<ItemResponseCreate> createNewItem(@RequestBody NewItemRequest request) {
        long categoryId = request.getCategory();
        if (!categoryService.categoryExist(categoryId)) {
            throw new CategoryNotFoundException();
        }

        Item item = itemService.getItemByRequest(request);

        String directory = String.valueOf(categoryId);
        List<Photo> photoList = photoService.convertBase64ListToPhotoList(request.getPhotos(), directory, item);

        item.setPhotoPreview(photoList.get(0).getUrl());
        item.setPhoto(photoList);

        Item createdItem = itemService.create(item);
        return ResponseEntity.status(200).body(new ItemResponseCreate(createdItem.getId()));
    }


    @DeleteMapping("/remove/{item_id}")
    public ResponseEntity<Void> removeItem(@PathVariable(name = "item_id") long id) {
        if (!itemService.itemExist(id)) {
            throw new ItemNotFoundException();
        }
        photoService.removePhotosFromStorage(itemService.getItemById(id));
        itemService.remove(id);
        return ResponseEntity.ok().build();
    }
}

record ItemResponseCreate(long id) {}
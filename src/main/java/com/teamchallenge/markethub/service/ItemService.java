package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.controller.filter.ItemsFilterParams;
import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.dto.item.ItemsResponse;
import com.teamchallenge.markethub.dto.item.NewItemRequest;
import com.teamchallenge.markethub.model.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    Item create(Item item);
    ItemCardResponse getItemCardById(long id);

    List<ItemResponse> getAllItemByCategoryId(ItemsFilterParams filterParams, Pageable pageable);

    List<ItemResponse> getAllItemBySubCategoryId(ItemsFilterParams filterParams, Pageable pageable);

    ItemResponse getItemById(long id);

    int getCountItemsByCategoryId(long categoryId);

    int getCountItemsBySubCategoryId(long subCategoryId);

    ItemsResponse getTopSellerList();

    ItemsResponse shares();

    Item getItemByRequest(NewItemRequest request);
}
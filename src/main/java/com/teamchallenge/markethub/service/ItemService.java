package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Item;

import java.util.List;

public interface ItemService {
    ItemResponse findItemById(long id) throws ItemNotFoundException;

    List<ItemResponse> getAllItemByCategoryId(long categoryId);

    List<ItemResponse> getAllItemBySubCategoryId(long subCategoryId);
}

package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    ItemCardResponse getItemCardById(long id);

    List<ItemResponse> getAllItemByCategoryId(long categoryId, Pageable pageable);

    List<ItemResponse> getAllItemBySubCategoryId(long subCategoryId, Pageable pageable);

    ItemResponse getItemById(long id);

    int getCountItemsByCategoryId(long categoryId);
    int getCountItemsBySubCategoryId(long subCategoryId);
}

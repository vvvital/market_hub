package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.item.ItemDetailResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.error.exception.SubCategoryNotFoundException;
import com.teamchallenge.markethub.error.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    ItemDetailResponse findItemById(long id) throws ItemNotFoundException, UserNotFoundException, CategoryNotFoundException, SubCategoryNotFoundException;

    List<ItemResponse> getAllItemByCategoryId(long categoryId, Pageable pageable);

    List<ItemResponse> getAllItemBySubCategoryId(long subCategoryId, Pageable pageable);

    ItemResponse findItem(long id);
}

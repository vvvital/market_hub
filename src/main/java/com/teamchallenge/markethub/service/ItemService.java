package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.item.ItemDetailsResponse;
import com.teamchallenge.markethub.dto.item.detail.ItemDetails;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.error.exception.SubCategoryNotFoundException;
import com.teamchallenge.markethub.error.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    ItemDetailsResponse findItemById(long id) throws ItemNotFoundException, UserNotFoundException, CategoryNotFoundException, SubCategoryNotFoundException;

    List<ItemDetails> getAllItemByCategoryId(long categoryId, Pageable pageable);

    List<ItemDetails> getAllItemBySubCategoryId(long subCategoryId, Pageable pageable);

    ItemDetails findItem(long id);

    int getCountItemsByCategoryId(long categoryId);
    int getCountItemsBySubCategoryId(long subCategoryId);
}

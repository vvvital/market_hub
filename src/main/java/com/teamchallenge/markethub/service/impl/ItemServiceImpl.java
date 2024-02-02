package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.item.ItemDetailsResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.ErrorMessages;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Category;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.SubCategory;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.repository.CategoryRepository;
import com.teamchallenge.markethub.repository.ItemRepository;
import com.teamchallenge.markethub.repository.SubCategoryRepository;
import com.teamchallenge.markethub.repository.UserRepository;
import com.teamchallenge.markethub.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public ItemDetailsResponse findItemById(long id) throws ItemNotFoundException {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(ErrorMessages.ITEM_NOT_FOUND.text()));
        User seller = userRepository.findById(item.getSeller().getId()).get();
        Category category = categoryRepository.findById(item.getCategory().getId()).get();
        SubCategory subCategory = subCategoryRepository.findById(item.getSubCategory().getId()).get();
        return ItemDetailsResponse.convertToItemDetailsResponse(item, category, subCategory, seller);
    }

    @Override
    public List<ItemResponse> getAllItemByCategoryId(long categoryId, Pageable pageable) {
        return itemRepository.findAllByCategoryId(categoryId,
                        PageRequest.of(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "sold"))
                        )).stream()
                .map(ItemResponse::convertToItemResponse).toList();
    }

    @Override
    public List<ItemResponse> getAllItemBySubCategoryId(long subCategoryId, Pageable pageable) {
        return itemRepository.findAllByCategoryId(subCategoryId,
                        PageRequest.of(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "sold"))
                        )).stream()
                .map(ItemResponse::convertToItemResponse).toList();
    }

    @Override
    public ItemResponse findItem(long id) {
        return ItemResponse.convertToItemResponse(itemRepository.findById(id).get());
    }
}

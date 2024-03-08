package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.controller.filter.ItemsFilterParams;
import com.teamchallenge.markethub.dto.item.ItemCardResponse;
import com.teamchallenge.markethub.dto.item.ItemResponse;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.repository.ItemRepository;
import com.teamchallenge.markethub.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.teamchallenge.markethub.controller.filter.FilterDefaultValues.*;
import static com.teamchallenge.markethub.controller.filter.Filter.doFilter;
import static com.teamchallenge.markethub.error.ErrorMessages.ITEM_NOT_FOUND;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final static String DEFAULT_SORT = "sold";

    private final ItemRepository itemRepository;

    @Override
    public ItemCardResponse getItemCardById(long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
        return ItemCardResponse.convertToItemCardResponse(item);
    }

    @Override
    public List<ItemResponse> getAllItemByCategoryId(ItemsFilterParams filterParams, Pageable pageable) {
        return itemRepository.findAll(doFilter(filterParams, ATTR_CATEGORY), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, DEFAULT_SORT)))).stream().map(ItemResponse::convertToItemResponse).toList();
    }

    @Override
    public List<ItemResponse> getAllItemBySubCategoryId(ItemsFilterParams filterParams, Pageable pageable) {
        return itemRepository.findAll(doFilter(filterParams, ATTR_SUB_CATEGORY), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, DEFAULT_SORT)))).stream().map(ItemResponse::convertToItemResponse).toList();
    }

    /*
        stub method, need refactoring
     */
    @Override
    public ItemResponse getItemById(long id) {
        return ItemResponse.convertToItemResponse(itemRepository.findById(id).get());
    }

    @Override
    public int getCountItemsByCategoryId(long categoryId) {
        return itemRepository.countByCategoryId(categoryId);
    }

    @Override
    public int getCountItemsBySubCategoryId(long subCategoryId) {
        return itemRepository.countBySubCategoryId(subCategoryId);
    }
}

package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.item.ItemDetailsResponse;
import com.teamchallenge.markethub.dto.item.detail.ItemDetails;
import com.teamchallenge.markethub.error.ErrorMessages;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.repository.ItemRepository;
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

    @Override
    public ItemDetailsResponse findItemById(long id) throws ItemNotFoundException {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(ErrorMessages.ITEM_NOT_FOUND.text()));
        return ItemDetailsResponse.convertToItemDetailsResponse(item);
    }

    @Override
    public List<ItemDetails> getAllItemByCategoryId(long categoryId, Pageable pageable) {
        return itemRepository.findAllByCategoryId(categoryId,
                        PageRequest.of(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "sold"))
                        )).stream()
                .map(ItemDetails::convertToItemDetails).toList();
    }

    @Override
    public List<ItemDetails> getAllItemBySubCategoryId(long subCategoryId, Pageable pageable) {
        return itemRepository.findAllBySubCategoryId(subCategoryId,
                        PageRequest.of(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "sold"))
                        )).stream()
                .map(ItemDetails::convertToItemDetails).toList();
    }

    @Override
    public ItemDetails findItem(long id) {
        return ItemDetails.convertToItemDetails(itemRepository.findById(id).get());
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

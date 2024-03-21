package com.teamchallenge.markethub.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.error.exception.SubCategoryNotFoundException;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.SubCategory;
import com.teamchallenge.markethub.repository.ItemRepository;
import com.teamchallenge.markethub.repository.SubCategoryRepository;
import com.teamchallenge.markethub.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    private final ItemRepository itemRepository;

    @Override
    public List<SubCategoryResponse> findAllSubCategoriesByParent(Long parentId) {
        return subCategoryRepository.findAllByParentId(parentId).stream()
                .map(SubCategoryResponse::convertToSubCategoryResponse).toList();
    }

    @Override
    public Set<String> getBrandsBySubcategory(Long subCategoryId) throws SubCategoryNotFoundException {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(()->new SubCategoryNotFoundException("Sub category wasn't found"));
        List<Item> items = itemRepository.findItemsBySubCategory(subCategory);
        return items.stream()
                .map(Item::getBrand)
                .collect(Collectors.toSet());
    }

}

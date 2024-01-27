package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.error.ErrorMessages;
import com.teamchallenge.markethub.error.exception.SubCategoryNotFoundException;
import com.teamchallenge.markethub.model.SubCategory;
import com.teamchallenge.markethub.repository.SubCategoryRepository;
import com.teamchallenge.markethub.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    @Override
    public List<SubCategory> findAllSubCategoriesByParent(Long parentId) {
        return subCategoryRepository.findAllByParentId(parentId);
    }

    @Override
    public SubCategory findSubCategoryById(Long id) throws SubCategoryNotFoundException {
        Optional<SubCategory> category = subCategoryRepository.findById(id);
        return category.orElseThrow(() -> new SubCategoryNotFoundException(ErrorMessages.SUB_CATEGORY_NOT_FOUND.text()));
    }
}

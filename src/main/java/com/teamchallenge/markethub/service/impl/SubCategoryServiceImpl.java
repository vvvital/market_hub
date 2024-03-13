package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.repository.SubCategoryRepository;
import com.teamchallenge.markethub.service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Cacheable("sub_category")
    @Override
    public List<SubCategoryResponse> findAllSubCategoriesByParent(Long parentId) {
        return subCategoryRepository.findAllByParentId(parentId).stream()
                .map(SubCategoryResponse::convertToSubCategoryResponse).toList();
    }
}

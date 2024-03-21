package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.model.SubCategory;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Set;

public interface SubCategoryService {
    List<SubCategoryResponse> findAllSubCategoriesByParent(Long parentId);

    Set<String> getBrandsBySubcategory(Long subCategoryId) throws ChangeSetPersister.NotFoundException;
}

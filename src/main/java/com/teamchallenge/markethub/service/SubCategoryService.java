package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.model.SubCategory;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponse> findAllSubCategoriesByParent(Long parentId);

    List<String> getBrandsBySubcategory(Long subCategoryId) throws ChangeSetPersister.NotFoundException;
}

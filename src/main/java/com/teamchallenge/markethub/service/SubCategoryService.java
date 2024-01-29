package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponse> findAllSubCategoriesByParent(Long parentId);
}

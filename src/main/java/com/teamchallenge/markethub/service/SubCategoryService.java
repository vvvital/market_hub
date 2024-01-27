package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.error.exception.SubCategoryNotFoundException;
import com.teamchallenge.markethub.model.SubCategory;

import java.util.List;

public interface SubCategoryService {
    List<SubCategory> findAllSubCategoriesByParent(Long parentId);

    SubCategory findSubCategoryById(Long id) throws SubCategoryNotFoundException;
}

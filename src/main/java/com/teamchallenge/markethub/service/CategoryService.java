package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.category.CategoryResponse;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllCategories();

    CategoryResponse findCategoryById(Long id) throws CategoryNotFoundException;
}

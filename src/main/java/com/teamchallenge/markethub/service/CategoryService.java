package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.category.CategoryResponse;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;
import com.teamchallenge.markethub.model.Category;

import java.util.List;

public interface CategoryService {
    Category findCategoryById(long id);
    List<CategoryResponse> findAllCategories();

    CategoryResponse findCategoryById(Long id) throws CategoryNotFoundException;
    Boolean categoryExist(long id);
}

package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.category.CategoryResponse;
import com.teamchallenge.markethub.error.ErrorMessages;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;
import com.teamchallenge.markethub.model.Category;
import com.teamchallenge.markethub.repository.CategoryRepository;
import com.teamchallenge.markethub.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Cacheable("category")
    @Override
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::convertToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse findCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return CategoryResponse.convertToCategoryResponse(category.orElseThrow(
                () -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND)));
    }
}

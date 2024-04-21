package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.category.CategoryResponse;
import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.service.impl.CategoryServiceImpl;
import com.teamchallenge.markethub.service.impl.SubCategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/markethub/categories")
public class CategoryController {

    @Value("${storage.path}")
    private String storagePath;
    private final CategoryServiceImpl categoryService;
    private final SubCategoryServiceImpl subCategoryService;
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);


    public CategoryController(CategoryServiceImpl categoryService, SubCategoryServiceImpl subCategoryService) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryResponseList = categoryService.findAllCategories();
        return ResponseEntity.status(200).body(categoryResponseList);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = "category_id") Long id) {
        CategoryResponse category = categoryService.findCategoryById(id);
        return ResponseEntity.status(200).body(category);
    }

    @GetMapping("/{category_id}/sub-categories")
    public ResponseEntity<List<SubCategoryResponse>> getAllSubCategories(@PathVariable(name = "category_id") Long parentId) {
        List<SubCategoryResponse> subCategoryResponseList = subCategoryService.findAllSubCategoriesByParent(parentId);
        return ResponseEntity.status(200).body(subCategoryResponseList);
    }

    @GetMapping("/{category_id}/{filename}")
    public ResponseEntity<byte[]> getPhotoPreview(@PathVariable(name = "category_id") String dir, @PathVariable(name = "filename") String filename) {
        try {
            Path path = Paths.get(storagePath + dir + "/", filename);
            if (Files.exists(path)) {
                byte[] bytes = Files.readAllBytes(path);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return ResponseEntity.status(200).headers(headers).body(bytes);
            } else {
                log.error("photo not found");
                return ResponseEntity.status(404).build();
            }
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
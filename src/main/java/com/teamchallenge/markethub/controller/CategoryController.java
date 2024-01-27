package com.teamchallenge.markethub.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.teamchallenge.markethub.dto.category.CategoryResponse;
import com.teamchallenge.markethub.dto.category.sub_category.SubCategoryResponse;
import com.teamchallenge.markethub.error.exception.CategoryNotFoundException;
import com.teamchallenge.markethub.model.Category;
import com.teamchallenge.markethub.model.SubCategory;
import com.teamchallenge.markethub.service.impl.CategoryServiceImpl;
import com.teamchallenge.markethub.service.impl.SubCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/markethub/categories")
public class CategoryController {

    @Value("${bucket.name}")
    private String bucketName;
    private final CategoryServiceImpl categoryService;
    private final SubCategoryServiceImpl subCategoryService;
    private final AmazonS3 s3Client;

    public CategoryController(CategoryServiceImpl categoryService, SubCategoryServiceImpl subCategoryService, AmazonS3 s3Client) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.s3Client = s3Client;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryResponse> categoryResponseList = categories.stream()
                .map(CategoryResponse::convertToCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(categoryResponseList);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = "category_id") Long id) throws CategoryNotFoundException {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.status(200).body(CategoryResponse.convertToCategoryResponse(category));
    }

    @GetMapping("/{category_id}/sub-categories")
    public ResponseEntity<List<SubCategoryResponse>> getAllSubCategories(@PathVariable(name = "category_id") Long parentId) {
        List<SubCategory> subCategories = subCategoryService.findAllSubCategoriesByParent(parentId);
        List<SubCategoryResponse> subCategoryResponseList = subCategories.stream()
                .map(SubCategoryResponse::convertToSubCategoryResponse)
                .toList();
        return ResponseEntity.status(200).body(subCategoryResponseList);
    }


    @GetMapping("/{category_id}/{filename}")
    public ResponseEntity<byte[]> getPhotoPreview(@PathVariable(name = "category_id") String path, @PathVariable(name = "filename") String filename) {
        String bucketFilePath = "categories/" + path + "/" + filename;

        try (S3Object s3Object = s3Client.getObject(bucketName, bucketFilePath);
             S3ObjectInputStream objectInputStream = s3Object.getObjectContent()) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            InputStreamResource inputStreamResource = new InputStreamResource(objectInputStream);

            return ResponseEntity.status(200).headers(headers).body(inputStreamResource.getContentAsByteArray());

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    //todo: create method for return photo_preview
    //todo: create item controller
    //todo: create method top_sold and shares
    //todo: make refactoring category and sub_category service

}

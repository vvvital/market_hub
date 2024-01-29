package com.teamchallenge.markethub.dto.category.sub_category;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.SubCategory;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SubCategoryResponse(long id, String name, String photoPreview, String parent) {
    public static SubCategoryResponse convertToSubCategoryResponse(SubCategory subCategory) {
        return new SubCategoryResponse(subCategory.getId(), subCategory.getName(), subCategory.getPhoto(), subCategory.getParent().getName());
    }
}

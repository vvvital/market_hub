package com.teamchallenge.markethub.dto.category;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.Category;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CategoryResponse(long id, String name, String photoPreview) {

    public static CategoryResponse convertToCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getPhoto());
    }
}

package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.Item;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ItemResponse(long id, String name, BigDecimal price, String photoPreview, Boolean available, String category, String subCategory) {

    public static ItemResponse convertToItemResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), item.getPhotoPreview(), item.isAvailable(), item.getCategory().getName(), item.getSubCategory().getName());
    }
}

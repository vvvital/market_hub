package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ItemResponse(long id, String name, BigDecimal price, String photoPreview, Boolean available, LocalDateTime createAt, String category, String subCategory) {

    public static ItemResponse convertToItemResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), item.getPhotoPreview(), item.isAvailable(), item.getCreateAt(), item.getCategory().getName(), item.getSubCategory().getName());
    }
}

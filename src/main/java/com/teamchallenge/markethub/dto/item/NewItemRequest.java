package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class NewItemRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private List<String> photos;
    private int stockQuantity;
    private long owner;
    private long category;
    private long subCategory;
}

package com.teamchallenge.markethub.dto.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.OrderItemData;
import lombok.*;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItemDataRequest {
    private Long itemId;
    private String name;
    private int quantity;
    private BigDecimal price;
    private Long categoryId;
    private Long subCategoryId;

    public static OrderItemData convertToItemOrderInfo(OrderItemDataRequest request) {
        return OrderItemData.builder()
                .itemId(request.itemId)
                .name(request.name)
                .quantity(request.quantity)
                .price(request.price)
                .categoryId(request.categoryId)
                .subCategoryId(request.subCategoryId)
                .build();
    }
}

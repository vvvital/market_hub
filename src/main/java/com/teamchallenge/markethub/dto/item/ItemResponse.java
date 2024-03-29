package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.dto.item.detail.CategoryDetails;
import com.teamchallenge.markethub.dto.item.detail.SubCategoryDetails;
import com.teamchallenge.markethub.model.Item;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.teamchallenge.markethub.dto.item.util.ItemUtils.createCategoryDetails;
import static com.teamchallenge.markethub.dto.item.util.ItemUtils.createSubCategoryDetails;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private String photoPreview;
    private boolean available;
    private String brand;
    private LocalDateTime createAt;
    private CategoryDetails categoryDetails;
    private SubCategoryDetails subCategoryDetails;

    private ItemResponse() {
    }

    public static ItemResponse convertToItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(item.getId());
        itemResponse.setName(item.getName());
        itemResponse.setPrice(item.getPrice());
        itemResponse.setPhotoPreview(item.getPhotoPreview());
        itemResponse.setAvailable(item.isAvailable());
        itemResponse.setBrand(item.getBrand());
        itemResponse.setCreateAt(item.getCreateAt());
        itemResponse.setCategoryDetails(createCategoryDetails(item.getCategory()));
        itemResponse.setSubCategoryDetails(createSubCategoryDetails(item.getSubCategory()));
        return itemResponse;
    }
}

package com.teamchallenge.markethub.dto.item.detail;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.teamchallenge.markethub.dto.item.ItemUtils.createCategoryDetails;
import static com.teamchallenge.markethub.dto.item.ItemUtils.createSubCategoryDetails;

@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemDetails {
    private long id;
    private String name;
    private BigDecimal price;
    private String photoPreview;
    private boolean available;
    private String brand;
    private LocalDateTime createAt;
    private CategoryDetails categoryDetails;
    private SubCategoryDetails subCategoryDetails;


    public static ItemDetails convertToItemDetails(Item item) {
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setId(item.getId());
        itemDetails.setName(item.getName());
        itemDetails.setPrice(item.getPrice());
        itemDetails.setPhotoPreview(item.getPhotoPreview());
        itemDetails.setAvailable(item.isAvailable());
        itemDetails.setBrand(item.getBrand());
        itemDetails.setCreateAt(item.getCreateAt());
        itemDetails.setCategoryDetails(createCategoryDetails(item.getCategory()));
        itemDetails.setSubCategoryDetails(createSubCategoryDetails(item.getSubCategory()));
        return itemDetails;
    }
}

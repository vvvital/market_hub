package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.dto.item.detail.CategoryDetails;
import com.teamchallenge.markethub.dto.item.detail.SellerDetails;
import com.teamchallenge.markethub.dto.item.detail.SubCategoryDetails;
import com.teamchallenge.markethub.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.teamchallenge.markethub.dto.item.ItemUtils.*;

@NoArgsConstructor
@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemDetailsResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private String article;
    private String description;
    private String brand;
    private String photoPreview;
    private List<Photo> photo;
    private boolean available;
    private LocalDateTime createAt;
    private CategoryDetails categoryDetails;
    private SubCategoryDetails subCategoryDetails;
    private SellerDetails sellerDetails;

    public static ItemDetailsResponse convertToItemDetailsResponse(Item item) {
        ItemDetailsResponse itemDetailsResponse = new ItemDetailsResponse();
        itemDetailsResponse.setId(item.getId());
        itemDetailsResponse.setName(item.getName());
        itemDetailsResponse.setPrice(item.getPrice());
        itemDetailsResponse.setArticle(item.getArticle());
        itemDetailsResponse.setDescription(item.getDescription());
        itemDetailsResponse.setBrand(item.getBrand());
        itemDetailsResponse.setPhotoPreview(item.getPhotoPreview());
        itemDetailsResponse.setPhoto(item.getPhoto());
        itemDetailsResponse.setAvailable(item.isAvailable());
        itemDetailsResponse.setCreateAt(item.getCreateAt());
        itemDetailsResponse.setCategoryDetails(createCategoryDetails(item.getCategory()));
        itemDetailsResponse.setSubCategoryDetails(createSubCategoryDetails(item.getSubCategory()));
        itemDetailsResponse.setSellerDetails(createSellerDetails(item.getSeller()));
        return itemDetailsResponse;
    }
}



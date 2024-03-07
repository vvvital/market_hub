package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.dto.item.detail.CategoryDetails;
import com.teamchallenge.markethub.dto.item.detail.PhotoDetails;
import com.teamchallenge.markethub.dto.item.detail.SellerDetails;
import com.teamchallenge.markethub.dto.item.detail.SubCategoryDetails;
import com.teamchallenge.markethub.model.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.teamchallenge.markethub.dto.item.util.ItemUtils.*;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemCardResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private String article;
    private String description;
    private String brand;
    private String photoPreview;
    private List<PhotoDetails> photo;
    private boolean available;
    private LocalDateTime createAt;
    private CategoryDetails categoryDetails;
    private SubCategoryDetails subCategoryDetails;
    private SellerDetails sellerDetails;

    private ItemCardResponse() {
    }

    public static ItemCardResponse convertToItemCardResponse(Item item) {
        ItemCardResponse itemCardResponse = new ItemCardResponse();
        itemCardResponse.setId(item.getId());
        itemCardResponse.setName(item.getName());
        itemCardResponse.setPrice(item.getPrice());
        itemCardResponse.setArticle(item.getArticle());
        itemCardResponse.setDescription(item.getDescription());
        itemCardResponse.setBrand(item.getBrand());
        itemCardResponse.setPhotoPreview(item.getPhotoPreview());
        itemCardResponse.setPhoto(item.getPhoto().stream().map(PhotoDetails::convertToPhoto).collect(Collectors.toList()));
        itemCardResponse.setAvailable(item.isAvailable());
        itemCardResponse.setCreateAt(item.getCreateAt());
        itemCardResponse.setCategoryDetails(createCategoryDetails(item.getCategory()));
        itemCardResponse.setSubCategoryDetails(createSubCategoryDetails(item.getSubCategory()));
        itemCardResponse.setSellerDetails(createSellerDetails(item.getSeller()));
        return itemCardResponse;
    }
}



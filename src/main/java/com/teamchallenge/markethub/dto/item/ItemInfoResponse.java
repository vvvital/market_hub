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
import java.util.stream.Collectors;

import static com.teamchallenge.markethub.dto.item.ItemUtils.*;

@NoArgsConstructor
@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemInfoResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private String article;
    private String description;
    private String brand;
    private String photoPreview;
    private List<PhotoResponse> photo;
    private boolean available;
    private LocalDateTime createAt;
    private CategoryDetails categoryDetails;
    private SubCategoryDetails subCategoryDetails;
    private SellerDetails sellerDetails;

    public static ItemInfoResponse convertToItemDetailsResponse(Item item) {
        ItemInfoResponse itemInfoResponse = new ItemInfoResponse();
        itemInfoResponse.setId(item.getId());
        itemInfoResponse.setName(item.getName());
        itemInfoResponse.setPrice(item.getPrice());
        itemInfoResponse.setArticle(item.getArticle());
        itemInfoResponse.setDescription(item.getDescription());
        itemInfoResponse.setBrand(item.getBrand());
        itemInfoResponse.setPhotoPreview(item.getPhotoPreview());
        itemInfoResponse.setPhoto(item.getPhoto().stream().map(PhotoResponse::convertToPhoto).collect(Collectors.toList()));
        itemInfoResponse.setAvailable(item.isAvailable());
        itemInfoResponse.setCreateAt(item.getCreateAt());
        itemInfoResponse.setCategoryDetails(createCategoryDetails(item.getCategory()));
        itemInfoResponse.setSubCategoryDetails(createSubCategoryDetails(item.getSubCategory()));
        itemInfoResponse.setSellerDetails(createSellerDetails(item.getSeller()));
        return itemInfoResponse;
    }
}



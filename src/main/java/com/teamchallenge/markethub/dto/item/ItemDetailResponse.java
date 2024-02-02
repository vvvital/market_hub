package com.teamchallenge.markethub.dto.item;

import com.teamchallenge.markethub.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ItemDetailResponse(
        long id, String name, BigDecimal price, String article, String description, String brand, String photoPreview,
        List<Photo> photo,
        Boolean available, LocalDateTime createAt, CategoryDetails categoryDetails,
        SubCategoryDetails subCategoryDetails, SellerDetails sellerDetails) {

    public static ItemDetailResponse convertToItemDetailsResponse(Item item, Category category, SubCategory subCategory, User seller) {
        return new ItemDetailResponse(
                item.getId(), item.getName(), item.getPrice(), item.getArticle(), item.getDescription(),
                item.getBrand(), item.getPhotoPreview(), item.getPhoto(), item.isAvailable(), item.getCreateAt(),
                createCategoryDetails(category), createSubCategoryDetails(subCategory), createSellerDetails(seller)
        );

    }

    static CategoryDetails createCategoryDetails(Category category) {
        return new CategoryDetails(category.getId(), category.getName());
    }

    static SubCategoryDetails createSubCategoryDetails(SubCategory subCategoryDetails) {
        return new SubCategoryDetails(subCategoryDetails.getId(), subCategoryDetails.getName());
    }

    static SellerDetails createSellerDetails(User seller) {
        return new SellerDetails(seller.getId(), seller.getFirstname(), seller.getLastname());
    }

    record CategoryDetails(long id, String name) {

    }

    record SubCategoryDetails(long id, String name) {

    }

    record SellerDetails(long id, String firstname, String lastname) {

    }
}



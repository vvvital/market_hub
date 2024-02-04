package com.teamchallenge.markethub.dto.item;

import com.teamchallenge.markethub.dto.item.detail.CategoryDetails;
import com.teamchallenge.markethub.dto.item.detail.SellerDetails;
import com.teamchallenge.markethub.dto.item.detail.SubCategoryDetails;
import com.teamchallenge.markethub.model.Category;
import com.teamchallenge.markethub.model.SubCategory;
import com.teamchallenge.markethub.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemUtils {

    public static CategoryDetails createCategoryDetails(Category category) {
        return new CategoryDetails(category.getId(), category.getName());
    }

    public static SubCategoryDetails createSubCategoryDetails(SubCategory subCategoryDetails) {
        return new SubCategoryDetails(subCategoryDetails.getId(), subCategoryDetails.getName());
    }

    public static SellerDetails createSellerDetails(User seller) {
        return new SellerDetails(seller.getId(), seller.getFirstname() + " " + seller.getLastname());
    }
}

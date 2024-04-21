package com.teamchallenge.markethub.dto.item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.repository.CategoryRepository;
import com.teamchallenge.markethub.repository.SubCategoryRepository;
import com.teamchallenge.markethub.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class NewItemRequest {
    private String name, description;
    private double price;
    private String brand;
    private List<String> photos;
    private int stockQuantity;
    private long owner, category, subCategory;
    private static final int DEFAULT_SOLD = 0;


    public static Item convertToNewItem(NewItemRequest request, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, UserRepository userRepository) {
        return Item.builder()
                .name(request.getName())
                .price(BigDecimal.valueOf(request.getPrice()))
                .createAt(LocalDateTime.now())
                .available(true)
                .stockQuantity(request.getStockQuantity())
                .category(categoryRepository.findById(request.getCategory()).orElseThrow())
                .subCategory(subCategoryRepository.findById(request.getSubCategory()).orElseThrow())
                .sold(DEFAULT_SOLD)
                .description(request.getDescription())
                .brand(request.getBrand())
                .seller(userRepository.findById((int) request.getOwner()).orElseThrow())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewItemRequest that = (NewItemRequest) o;
        return owner == that.owner && category == that.category
                && subCategory == that.subCategory && Objects.equals(name, that.name) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, owner, category, subCategory);
    }
}

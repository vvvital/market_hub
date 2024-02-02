package com.teamchallenge.markethub.repository;

import com.teamchallenge.markethub.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepositoryTest extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryId(long categoryId);
    List<Item> findAllBySubCategoryId(long subCategoryId);
}

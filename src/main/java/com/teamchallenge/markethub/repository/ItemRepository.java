package com.teamchallenge.markethub.repository;

import com.teamchallenge.markethub.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategoryId(long categoryId);
    List<Item> findAllBySubCategoryId(long subCategoryId);
}

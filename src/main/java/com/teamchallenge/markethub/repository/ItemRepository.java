package com.teamchallenge.markethub.repository;

import com.teamchallenge.markethub.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    int countByCategoryId(long categoryId);

    int countBySubCategoryId(long subCategoryId);

    boolean existsItemById(long id);
}

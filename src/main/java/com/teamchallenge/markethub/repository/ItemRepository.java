package com.teamchallenge.markethub.repository;

import com.teamchallenge.markethub.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {
    Page<Item> findAllByCategoryId(long categoryId, PageRequest pageRequest);

    List<Item> findAllBySubCategoryId(long subCategoryId, PageRequest pageRequest);
}

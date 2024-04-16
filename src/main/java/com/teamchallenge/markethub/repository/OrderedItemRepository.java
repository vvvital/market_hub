package com.teamchallenge.markethub.repository;

import com.teamchallenge.markethub.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
}

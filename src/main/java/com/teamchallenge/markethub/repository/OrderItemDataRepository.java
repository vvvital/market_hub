package com.teamchallenge.markethub.repository;

import com.teamchallenge.markethub.model.OrderItemData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDataRepository extends JpaRepository<OrderItemData, Long> {
}

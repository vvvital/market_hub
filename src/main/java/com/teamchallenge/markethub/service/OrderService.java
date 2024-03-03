package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.order.OrderRequest;
import com.teamchallenge.markethub.model.Order;

import java.util.List;

public interface OrderService {
    Order create(OrderRequest request);

    Order find(long orderId);

    List<Order> findAllOrdersByUser(String email);
}

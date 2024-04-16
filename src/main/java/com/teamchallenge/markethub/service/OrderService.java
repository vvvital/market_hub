package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.model.Order;

import java.util.List;

public interface OrderService {
    Order create(CreateOrderRequest request);

    Order find(long orderId);

    List<Order> findAllOrdersByUser(String email);
}

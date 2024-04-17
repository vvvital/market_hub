package com.teamchallenge.markethub.service;

import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.model.Order;
import com.teamchallenge.markethub.model.enums.Status;

import java.util.List;

public interface OrderService {
    Order create(CreateOrderRequest request);

    Order find(long orderId);

    List<Order> findAllOrdersByUser(String email);

    void remove(long orderId);
    void updateStatus(long orderId, Status status);
}

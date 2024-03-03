package com.teamchallenge.markethub.dto.item;

import com.teamchallenge.markethub.model.OrderItemData;

import java.util.List;

public record ItemOrderRequest(List<OrderItemData> items) {
}

package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.dto.order.OrderDataResponse;
import com.teamchallenge.markethub.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/markethub/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping()
    public ResponseEntity<OrderDataResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderDataResponse response = OrderDataResponse.convertOrderToOrderDataResponse(orderService.create(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDataResponse> getOrder(@PathVariable(name = "id") long id) {
        OrderDataResponse response = OrderDataResponse.convertOrderToOrderDataResponse(orderService.find(id));
        return ResponseEntity.ok(response);
    }

    //TODO: controller for remove order
    //TODO: controller for change status
}

package com.teamchallenge.markethub.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.dto.order.OrderDataResponse;
import com.teamchallenge.markethub.model.enums.Status;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") long id) {
        orderService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable(name = "id") long id, @RequestBody OrderStatusChangeRequest request) {
        orderService.updateStatus(id, request.newStatus());
        return ResponseEntity.ok().build();
    }
}
//todo: join users and orders

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record OrderStatusChangeRequest(Status newStatus){}
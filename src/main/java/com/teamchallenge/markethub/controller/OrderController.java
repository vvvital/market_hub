package com.teamchallenge.markethub.controller;

import com.teamchallenge.markethub.dto.order.OrderRequest;
import com.teamchallenge.markethub.dto.order.OrderResponse;
import com.teamchallenge.markethub.model.Order;
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
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest request) {
        orderService.create(request);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable(name = "id") long id) {
        //todo
        Order order = orderService.find(id);
        return null;
    }


    //TODO: controller for change status
}

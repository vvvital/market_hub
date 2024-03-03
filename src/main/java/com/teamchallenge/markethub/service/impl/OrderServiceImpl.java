package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.order.OrderItemDataRequest;
import com.teamchallenge.markethub.dto.order.OrderRequest;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.OrderItemData;
import com.teamchallenge.markethub.model.Order;
import com.teamchallenge.markethub.model.enums.Status;
import com.teamchallenge.markethub.repository.ItemRepository;
import com.teamchallenge.markethub.repository.OrderItemDataRepository;
import com.teamchallenge.markethub.repository.OrderRepository;
import com.teamchallenge.markethub.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemDataRepository orderItemDataRepository;
    private final ItemRepository itemRepository;

    @Override
    public Order create(@NonNull OrderRequest request) {
        if (request.getItems().isEmpty()) {
            throw new IllegalArgumentException("array items must not be empty");
        }
        List<OrderItemData> list = request.getItems().stream().map(OrderItemDataRequest::convertToItemOrderInfo).toList();

        for (OrderItemData itemData : list) {
            Item item = itemRepository.findById(itemData.getItemId()).orElseThrow();
            int itemCount = itemData.getQuantity();
            if (item.getStockQuantity() < itemCount) {
                throw new IllegalArgumentException("item don`t have enough quantity");
            } else {
                int updateStockQuantity = item.getStockQuantity() - itemCount;
                item.setStockQuantity(updateStockQuantity);
                itemRepository.save(item);
            }
        }
        orderItemDataRepository.saveAll(list);

        return orderRepository.save
                (Order.builder()
                        .customerFirstname(request.getCustomerFirstname())
                        .customerLastname(request.getCustomerLastname())
                        .customerEmail(request.getCustomerEmail())
                        .customerPhone(request.getCustomerPhone())
                        .customerCity(request.getCustomerCity())
                        .items(list)
                        .totalQuantity(request.getTotalQuantity())
                        .deliveryService(request.getDeliveryService())
                        .postalAddress(request.getPostalAddress())
                        .totalAmount(request.getTotalAmount())
                        .createAt(LocalDate.now())
                        .status(Status.IN_PROCESS)
                        .build());
    }

    @Override
    public Order find(long orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("order_id cannot be 0 or less than 0");
        }
        return orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Order> findAllOrdersByUser(@NonNull String email) {
        return orderRepository.findAllByCustomerEmail(email);
    }
}

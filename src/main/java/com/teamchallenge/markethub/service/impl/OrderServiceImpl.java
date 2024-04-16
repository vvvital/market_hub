package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.error.exception.EmptyArrayDataAboutTheOrderedItemsException;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.error.exception.NotEnoughQuantityItemException;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.Order;
import com.teamchallenge.markethub.model.OrderedItem;
import com.teamchallenge.markethub.model.enums.Status;
import com.teamchallenge.markethub.repository.ItemRepository;
import com.teamchallenge.markethub.repository.OrderedItemRepository;
import com.teamchallenge.markethub.repository.OrderRepository;
import com.teamchallenge.markethub.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;
    private final ItemRepository itemRepository;

    @Override
    public Order create(@NonNull CreateOrderRequest request) {
        if (request.getItems().isEmpty()) {
            throw new EmptyArrayDataAboutTheOrderedItemsException();

        }
        List<OrderedItem> orderedItemList = new ArrayList<>(5);

        for (CreateOrderRequest.DateAboutTheOrderedItem dateAboutOrderedItem : request.getItems()) {
            Item item = itemRepository.findById(dateAboutOrderedItem.itemId()).orElseThrow(ItemNotFoundException::new);
            OrderedItem orderedItem = builderOrderedItem(dateAboutOrderedItem, item);
            updateQuantityAndSoldItem(orderedItem, item);
            orderedItemList.add(orderedItem);
        }
        orderedItemRepository.saveAll(orderedItemList);

        return orderRepository.save(builderNewOrder(request, orderedItemList));
    }

    private OrderedItem builderOrderedItem(CreateOrderRequest.DateAboutTheOrderedItem dateAboutOrderedItem, Item item) {
        return OrderedItem.builder()
                .itemId(item.getId())
                .name(item.getName())
                .photoPreview(item.getPhotoPreview())
                .quantity(dateAboutOrderedItem.quantity())
                .price(item.getPrice())
                .categoryId(item.getCategory().getId())
                .subCategoryId(item.getSubCategory().getId())
                .build();
    }

    private void updateQuantityAndSoldItem(OrderedItem orderedItem, Item item) {
        int requestQuantityItem = orderedItem.getQuantity();
        int currentQuantityItem = item.getStockQuantity();

        if (currentQuantityItem < requestQuantityItem) {
            throw new NotEnoughQuantityItemException(item.getId());
        } else {
            item.setStockQuantity(currentQuantityItem - requestQuantityItem);
            item.setSold(item.getSold() + requestQuantityItem);
            itemRepository.save(item);
        }
    }

    private Order builderNewOrder(CreateOrderRequest request, List<OrderedItem> orderedItems) {
        int totalQuantity = 0;
        double totalAmount = 0.0;

        for (OrderedItem orderedItem : orderedItems) {
            totalQuantity += orderedItem.getQuantity();
            totalAmount += orderedItem.getPrice().doubleValue() * orderedItem.getQuantity();
        }

        return Order.builder()
                .customerFirstname(request.getCustomerFirstname())
                .customerLastname(request.getCustomerLastname())
                .customerEmail(request.getCustomerEmail())
                .customerPhone(request.getCustomerPhone())
                .customerCity(request.getCustomerCity())
                .items(orderedItems)
                .totalQuantity(totalQuantity)
                .deliveryService(request.getDeliveryService())
                .postalAddress(request.getPostalAddress())
                .totalAmount(BigDecimal.valueOf(totalAmount))
                .createAt(LocalDateTime.now())
                .status(Status.NEW)
                .build();
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

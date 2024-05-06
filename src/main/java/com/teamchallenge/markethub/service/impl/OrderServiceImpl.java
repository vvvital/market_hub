package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.dto.order.CreateOrderRequest;
import com.teamchallenge.markethub.error.exception.EmptyArrayDataAboutTheOrderedItemsException;
import com.teamchallenge.markethub.error.exception.ItemNotFoundException;
import com.teamchallenge.markethub.error.exception.NotEnoughQuantityItemException;
import com.teamchallenge.markethub.error.exception.OrderNotFoundException;
import com.teamchallenge.markethub.model.Item;
import com.teamchallenge.markethub.model.Order;
import com.teamchallenge.markethub.model.OrderedItem;
import com.teamchallenge.markethub.model.enums.Status;
import com.teamchallenge.markethub.repository.ItemRepository;
import com.teamchallenge.markethub.repository.OrderedItemRepository;
import com.teamchallenge.markethub.repository.OrderRepository;
import com.teamchallenge.markethub.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;
    private final ItemRepository itemRepository;
    private final static int DEFAULT_AMOUNT_ITEMS = 5;

    @Transactional
    @Override
    public Order create(@NonNull CreateOrderRequest request) {
        if (request.getItems().isEmpty()) {
            throw new EmptyArrayDataAboutTheOrderedItemsException();
        }
        List<OrderedItem> orderedItemList = new ArrayList<>(DEFAULT_AMOUNT_ITEMS);
        List<Item> updatedItemList = new ArrayList<>(DEFAULT_AMOUNT_ITEMS);
        for (CreateOrderRequest.DateAboutTheOrderedItem dateAboutOrderedItem : request.getItems()) {
            Item item = itemRepository.findById(dateAboutOrderedItem.itemId()).orElseThrow(ItemNotFoundException::new);
            OrderedItem orderedItem = buildOrderedItem(dateAboutOrderedItem, item);
            updatedItemList.add(updateQuantityItem(orderedItem, item));
            orderedItemList.add(orderedItem);
        }
        orderedItemRepository.saveAll(orderedItemList);
        itemRepository.saveAll(updatedItemList);
        return orderRepository.save(buildNewOrder(request, orderedItemList));
    }

    private OrderedItem buildOrderedItem(CreateOrderRequest.DateAboutTheOrderedItem dateAboutOrderedItem, Item item) {
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

    private Item updateQuantityItem(OrderedItem orderedItem, Item item) {
        int requestQuantityItem = orderedItem.getQuantity();
        int currentQuantityItem = item.getStockQuantity();

        if (currentQuantityItem < requestQuantityItem) {
            throw new NotEnoughQuantityItemException(item.getId());
        } else {
            int updatedStockQuantity = currentQuantityItem - requestQuantityItem;
            item.setStockQuantity(updatedStockQuantity);
            if (updatedStockQuantity == 0)
                item.setAvailable(false);
        }
        return item;
    }

    private Order buildNewOrder(CreateOrderRequest request, List<OrderedItem> orderedItems) {
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
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<Order> findAllOrdersByUser(@NonNull String email) {
        return orderRepository.findAllByCustomerEmail(email);
    }

    @Override
    public void remove(long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException();
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public void updateStatus(long orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        switch (status) {
            case NEW, IN_PROCESS -> order.setStatus(status);
            case SUCCESS -> {
                updateItem(order, this::increaseSoldItem);
                order.setStatus(status);
            }
            case CANCELED -> {
                if (order.getStatus() == Status.SUCCESS) {
                    updateItem(order, this::reduceSoldItem);
                }
                updateItem(order, this::increaseStockQuantityItem);
                order.setStatus(status);
            }
        }
        orderRepository.save(order);
    }

    private void updateItem(Order order, BiConsumer<Item, Integer> action) {
        List<Item> updateItemList = new ArrayList<>(DEFAULT_AMOUNT_ITEMS);
        for (OrderedItem orderedItem : order.getItems()) {
            Optional<Item> optionalItem = itemRepository.findById(orderedItem.getItemId());
            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                action.accept(item, orderedItem.getQuantity());
                updateItemList.add(item);
            }
        }
        if (!updateItemList.isEmpty())
            itemRepository.saveAll(updateItemList);
    }

    private void increaseSoldItem(Item item, int quantity) {
        item.setSold(item.getSold() + quantity);
    }

    private void reduceSoldItem(Item item, int quantity) {
        item.setSold(item.getSold() - quantity);
    }

    private void increaseStockQuantityItem(Item item, int quantity) {
        item.setStockQuantity(item.getStockQuantity() + quantity);
    }
}

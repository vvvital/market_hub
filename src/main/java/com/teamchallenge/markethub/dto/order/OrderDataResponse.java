package com.teamchallenge.markethub.dto.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.Order;
import com.teamchallenge.markethub.model.OrderedItem;
import com.teamchallenge.markethub.model.enums.DeliveryService;
import com.teamchallenge.markethub.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDataResponse {
    private long orderNumber;
    private LocalDateTime orderDate;
    private List<OrderGoodsData> orderGoods;
    private OrderUserData orderClient;
    private int orderTotalQuantity;
    private double orderTotalAmount;
    private Status orderStatus;

    public static OrderDataResponse convertOrderToOrderDataResponse(Order order) {
        return OrderDataResponse.builder()
                .orderNumber(order.getId())
                .orderDate(order.getCreateAt())
                .orderGoods(order.getItems().stream().map(OrderGoodsData::convertToOrderGoodsData).toList())
                .orderClient(OrderUserData.convertToOrderUserData(order.getCustomerFirstname(), order.getCustomerLastname(),
                        order.getCustomerPhone(), order.getPostalAddress(), order.getDeliveryService()))
                .orderTotalQuantity(order.getTotalQuantity())
                .orderTotalAmount(order.getTotalAmount().doubleValue())
                .orderStatus(order.getStatus())
                .build();
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private record OrderGoodsData(String title, String photoUrl, double price, int quantity){
        public static OrderGoodsData convertToOrderGoodsData(OrderedItem orderedItem) {
            return new OrderGoodsData(orderedItem.getName(), orderedItem.getPhotoPreview(),
                    orderedItem.getPrice().doubleValue(), orderedItem.getQuantity());
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private record OrderUserData(String firstname, String lastname, String phone, String address, DeliveryService deliveryService){
        public static OrderUserData convertToOrderUserData(String firstname, String lastname, String phone, String address, DeliveryService deliveryService) {
            return new OrderUserData(firstname, lastname, phone, address, deliveryService);
        }
    }

}

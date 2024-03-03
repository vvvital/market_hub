package com.teamchallenge.markethub.dto.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.teamchallenge.markethub.model.enums.DeliveryService;
import lombok.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderRequest {
    private String customerFirstname;
    private String customerLastname;
    private String customerEmail;
    private String customerPhone;
    private String customerCity;
    private List<OrderItemDataRequest> items;
    private int totalQuantity;
    private DeliveryService deliveryService;
    private String postalAddress;
    private int totalAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return totalQuantity == that.totalQuantity && totalAmount == that.totalAmount &&
                Objects.equals(customerFirstname, that.customerFirstname) && Objects.equals(customerLastname, that.customerLastname) &&
                Objects.equals(customerEmail, that.customerEmail) && Objects.equals(customerPhone, that.customerPhone) &&
                Objects.equals(customerCity, that.customerCity) && Objects.equals(items, that.items) && deliveryService == that.deliveryService &&
                Objects.equals(postalAddress, that.postalAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerFirstname, customerLastname, customerEmail,
                customerPhone, customerCity, items, totalQuantity, deliveryService, postalAddress, totalAmount);
    }
}

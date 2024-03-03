package com.teamchallenge.markethub.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_item_data") //todo: change 'orders_items_data', add validation fields
public class OrderItemData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "sub_category_id", nullable = false)
    private Long subCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemData that = (OrderItemData) o;
        return quantity == that.quantity && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(categoryId, that.categoryId) && Objects.equals(subCategoryId, that.subCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, price, categoryId, subCategoryId);
    }

    @Override
    public String toString() {
        return "ItemOrderInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", subCategoryId=" + subCategoryId +
                '}';
    }
}

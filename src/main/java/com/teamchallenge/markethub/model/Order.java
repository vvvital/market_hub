package com.teamchallenge.markethub.model;

import com.teamchallenge.markethub.model.enums.DeliveryService;
import com.teamchallenge.markethub.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders", indexes = @Index(columnList = "customer_email"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "the firstname should not be empty")
    @Column(name = "customer_firstname", nullable = false)
    private String customerFirstname;

    @NotNull
    @NotBlank(message = "the lastname should not be empty")
    @Column(name = "customer_lastname", nullable = false)
    private String customerLastname;

    @NotNull
    @NotBlank(message = "the email should not be empty")
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @NotNull
    @NotBlank(message = "the phone should not be empty")
    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @NotNull
    @NotBlank(message = "the city should not be empty")
    @Column(name = "customer_city", nullable = false)
    private String customerCity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "data_ordered_item_id")
    )
    private List<OrderedItem> items;

    @NotNull
    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @NotNull
    @Column(name = "delivery_service", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DeliveryService deliveryService;

    @NotNull
    @NotBlank(message = "the postal_address should not be empty")
    @Column(name = "postal_address", nullable = false)
    private String postalAddress;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "create_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createAt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return totalAmount == order.totalAmount && Objects.equals(id, order.id) && Objects.equals(customerFirstname, order.customerFirstname) && Objects.equals(customerLastname, order.customerLastname) && Objects.equals(customerEmail, order.customerEmail) && Objects.equals(customerPhone, order.customerPhone) && Objects.equals(customerCity, order.customerCity) && Objects.equals(postalAddress, order.postalAddress) && Objects.equals(createAt, order.createAt) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerFirstname, customerLastname, customerEmail, customerPhone, customerCity, postalAddress, totalAmount, createAt, status);
    }
}


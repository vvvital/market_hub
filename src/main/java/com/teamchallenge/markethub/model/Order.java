package com.teamchallenge.markethub.model;

import com.teamchallenge.markethub.model.enums.DeliveryService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    //TODO: To finish entity 'Order'
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "has_account", nullable = false)
    private boolean hasAccount;

    @ManyToMany
    private List<Item> items;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "city", nullable = false)
    private String city;

    @Enumerated(value = EnumType.STRING)
    private DeliveryService deliveryService;

    @Column(name = "postal_address", nullable = false)
    private String postalAddress;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Column(name = "status", nullable = false)
    private boolean status;
}

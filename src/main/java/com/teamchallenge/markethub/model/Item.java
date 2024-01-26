package com.teamchallenge.markethub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "goods")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_seq",
            initialValue = 100,
            allocationSize = 28
    )
    private Long id;

    @NotBlank(message = "product name must not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "price must not be empty")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @NotBlank(message = "article must not be empty")
    @Column(name = "article", nullable = false, unique = true)
    private String article;

    @NotNull
    @Column(name = "available", nullable = false)
    private boolean available;

    @NotNull
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @Column(name = "photo_preview")
    private String photoPreview;

    @OneToMany(mappedBy = "item")
    private List<Photo> photo;

    @Column(name = "sold")
    private int sold;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "brand")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User sellerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(article, item.article) && Objects.equals(category, item.category) && Objects.equals(subCategory, item.subCategory) && Objects.equals(sellerId, item.sellerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, article, category, subCategory, sellerId);
    }
}

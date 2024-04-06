package com.teamchallenge.markethub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    //@NotBlank(message = "article must not be empty")
    @UuidGenerator
    @Column(name = "article", unique = true)
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

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<Photo> photo;

    @Column(name = "sold")
    private int sold;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "brand")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToMany
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> favoriteByUsers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(article, item.article)
                && Objects.equals(category, item.category) && Objects.equals(subCategory, item.subCategory) && Objects.equals(seller, item.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, article, category, subCategory, seller);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createAt=" + createAt +
                ", article='" + article + '\'' +
                ", available=" + available +
                ", stockQuantity=" + stockQuantity +
                ", category=" + category +
                ", subCategory=" + subCategory +
                ", photoPreview='" + photoPreview + '\'' +
                ", photo=" + photo +
                ", sold=" + sold +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", seller=" + seller +
                '}';
    }
}

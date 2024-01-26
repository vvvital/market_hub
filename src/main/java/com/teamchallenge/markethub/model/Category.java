package com.teamchallenge.markethub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_seq",
            initialValue = 100,
            allocationSize = 75
    )
    private Long id;

    @NotBlank(message = "category name must not be empty")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "photo_preview")
    private String photo;

    @OneToMany(mappedBy = "parent")
    private List<SubCategory> subCategoryList;

    @OneToMany(mappedBy = "category")
    private List<Item> itemList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

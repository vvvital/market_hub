package com.teamchallenge.markethub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_sequence")
    @SequenceGenerator(
            name = "photo_sequence",
            sequenceName = "photo_seq",
            initialValue = 1000,
            allocationSize = 132
    )
    private Long id;

    @NotBlank(message = "photo name must not be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "photo url must not be empty")
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

}

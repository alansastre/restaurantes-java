package com.restaurantes.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private Integer rating;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Builder.Default // para que el builder no ponga este campo a null
    private LocalDateTime creationDate = LocalDateTime.now();

    @ToString.Exclude
    @ManyToOne
    private Restaurant restaurant;

    @ToString.Exclude
    @ManyToOne
    private Dish dish;

    // @ToString.Exclude
//    @ManyToOne
//    private User user;


}

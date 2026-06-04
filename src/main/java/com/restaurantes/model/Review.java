package com.restaurantes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede tener más de 255 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "La valoración es obligatoria")
    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
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

    @ToString.Exclude
    @ManyToOne
    private User user;


}

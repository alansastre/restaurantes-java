package com.restaurantes.model;

import jakarta.persistence.*;

/*

SELECT * FROM RESTAURANT;

INSERT INTO restaurant (id, name) VALUES (1, '100 montaditos');
INSERT INTO restaurant (id, name, number_employees) VALUES (4, 'Prueba', 10);
INSERT INTO restaurant (id, name, number_employees, average_price, active) VALUES (8, 'Prueba', 10, 20.5, TRUE);

UPDATE restaurant
SET number_employees = 50
WHERE id = 1;

DELETE FROM restaurant WHERE id = 1;
 */
@Entity
@Table(name = "restaurantes")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double averagePrice;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active;

    private Integer numberEmployees;


    // Próximas tareas:
    // Enum
    // Fecha
    // Asociación con otra entidad/tabla

}

package com.restaurantes.model;

import jakarta.persistence.*;

import java.awt.*;
import java.util.ArrayList;

/*
localhost:8080/h2-console

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
public class Restaurant { // clase

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double averagePrice;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;

    private Integer numberEmployees;

    // bidireccional
//    @OneToMany
//    List<Employee> employees = new ArrayList<Employee>();

    // metodo constructor para crear Restaurantes con valores

    public Restaurant(String name, Double averagePrice, Integer numberEmployees) {
        this.name = name;
        this.averagePrice = averagePrice;
        this.numberEmployees = numberEmployees;
    }

    // metodo constructor vacío
    public Restaurant() {
    }

    // Métodos Getter y Setter
    // Get obtener, permite obtener un atributo
    // Set cambiar, permite cambiar un valor
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getNumberEmployees() {
        return numberEmployees;
    }

    public void setNumberEmployees(Integer numberEmployees) {
        this.numberEmployees = numberEmployees;
    }

    // Metodo toString para ver los datos de un restaurante al imprimirlo
    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", averagePrice=" + averagePrice +
                ", active=" + active +
                ", numberEmployees=" + numberEmployees +
                '}';
    }

    // Próximas tareas:
    // Enum
    // Fecha
    // Asociación con otra entidad/tabla

}

package com.restaurantes.model;

import jakarta.persistence.*;

/*
Platos por restaurante:
select d.name, d.price, r.name from dish d inner join restaurantes r on d.restaurant_id = r.id;

 con filtro:
 select
d.id as dish_id,
d.name as dish_name,
d.price as dish_price,
r.id as restaurant_id,
r.name as restaurant_name
from dish d
inner join restaurantes r on d.restaurant_id = r.id
where r.name = 'La Taberna';
 */
@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Column(length = 500)
    private String description; // 255

    private Double price;

    @Enumerated(EnumType.STRING)
    private DishType dishType;

    @ManyToOne
    private Restaurant restaurant;

    // constructor
    public Dish() {}
    public Dish(Long id, String name, String description, Double price, DishType dishType, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dishType = dishType;
        this.restaurant = restaurant;
    }

    // getter setter

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // tostring
    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dishType=" + dishType +
                ", restaurant=" + restaurant +
                '}';
    }
}

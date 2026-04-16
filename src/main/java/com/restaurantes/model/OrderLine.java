package com.restaurantes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lineas_pedido")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Dish dish;

    public OrderLine() {}

    public OrderLine(Integer quantity, Order order, Dish dish) {
        this.quantity = quantity;
        this.order = order;
        this.dish = dish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }


    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}

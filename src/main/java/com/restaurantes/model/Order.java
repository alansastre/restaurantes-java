package com.restaurantes.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
// CUIDADO: order es una palabra reservada en db
// Por eso le cambiamos el nombre a la tabla
@Table(name = "pedidos")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date =  LocalDateTime.now();

    private Double totalPrice;

    private Double tip;

    private Integer tableNumber;

    private Integer numPeople;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne
    private Restaurant restaurant;

    public Order() {
    }

    public Order(Double tip, Integer tableNumber, Integer numPeople, Restaurant restaurant) {
        this.tip = tip;
        this.tableNumber = tableNumber;
        this.numPeople = numPeople;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", tip=" + tip +
                ", tableNumber=" + tableNumber +
                ", numPeople=" + numPeople +
                ", status=" + status +
                '}';
    }
}

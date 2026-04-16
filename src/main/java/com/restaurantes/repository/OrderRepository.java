package com.restaurantes.repository;

import com.restaurantes.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // traer todos los pedidos de un restaurante
    // findByRestaurantId
}
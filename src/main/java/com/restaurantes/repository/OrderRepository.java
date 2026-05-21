package com.restaurantes.repository;

import com.restaurantes.model.Order;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_IdOrderByDateDesc(Long id);


//    List<Order> findByUser_IdOrderByDateDesc(Long id);
}
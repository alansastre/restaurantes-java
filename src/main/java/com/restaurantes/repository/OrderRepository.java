package com.restaurantes.repository;

import com.restaurantes.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_IdOrderByDateDesc(Long id);

    long countByUser_Id(Long id);

    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0.0) FROM Order o WHERE o.user.id = :userId
    """)
    double calculateTotalMoneySpentByUserId(Long userId);


//    List<Order> findByUser_IdOrderByDateDesc(Long id);
}
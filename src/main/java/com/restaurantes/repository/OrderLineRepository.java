package com.restaurantes.repository;

import com.restaurantes.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {


    @Query("""
        
                SELECT SUM(ol.quantity * ol.dish.price)
                FROM OrderLine ol where ol.order.id = ?1
                        
        """)
    Double calculateTotalPrice(Long orderId);
}
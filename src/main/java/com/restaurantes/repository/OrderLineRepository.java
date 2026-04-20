package com.restaurantes.repository;

import com.restaurantes.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {


    /*
    DDD - Domain Driven Design, podemos mover este cálculo a la raíz que es Order, que es la principal
    ya que este OrderLine es un agregado de esa raíz que añade información extra
     */
    @Query("""
        SELECT SUM(ol.quantity * ol.dish.price)
        FROM OrderLine ol where ol.order.id = ?1
        """)
    Double calculateTotalPrice(Long orderId);
}
package com.restaurantes.repository;

import com.restaurantes.model.Restaurant;
import com.restaurantes.model.enums.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
//    @Transactional
//    @Modifying
//    @Query("delete from Restaurant r where r.name = ?1")
//    void deleteByNameTodoGuay(String name);

    List<Restaurant> findByActiveTrue();
    Optional<Restaurant> findByIdAndActiveTrue(Long id);


    // Query con filtros:
    @Query("""
        SELECT r from Restaurant r
        WHERE r.active = true
        AND (:foodType IS NULL OR r.foodType = :foodType)
        AND (:price IS NULL OR r.averagePrice <= :price)
        AND (:title IS NULL OR :title = '' OR LOWER(r.name) LIKE LOWER(CONCAT('%', :title, '%')))
        """)
    List<Restaurant> findActiveFiltering(
            @Param("foodType") FoodType foodType,
            @Param("price") Double price,
            @Param("title") String title
    );

    // Filtro por nombre

    // Filtro por foodType

    // Filtro por precio

    // Filtro por código postal

    // Si se vuelven muy complejos los filtros se puede usar:
    // API Specification
}
package com.restaurantes.repository;

import com.restaurantes.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_IdAndRestaurantIsNotNull(Long id);
    List<Favorite> findByUser_IdAndDishIsNotNull(Long id);

    Optional<Favorite> findByUser_IdAndRestaurantId(Long userId, Long restaurantId);
    Optional<Favorite> findByUser_IdAndDishId(Long userId, Long dishId);

    // verificar si un restaurante ya es favorito y así decidir mostrarlo diferente en la UI
    @Query("""
    select f.restaurant.id from Favorite f
        where f.user.id = :userId
        and f.restaurant IS NOT NULL
    """)
    Set<Long> findRestaurantIdsByUserId(@Param("userId") Long userId);

    @Query("""
    select f.dish.id from Favorite f
        where f.user.id = :userId
        and f.dish IS NOT NULL
    """)
    Set<Long> findDishIdsByUserId(@Param("userId") Long userId);
}
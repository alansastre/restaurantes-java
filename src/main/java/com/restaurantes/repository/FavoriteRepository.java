package com.restaurantes.repository;

import com.restaurantes.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_IdAndRestaurantIsNotNull(Long id);
    List<Favorite> findByUser_IdAndDishIsNotNull(Long id);

    Optional<Favorite> findByUser_IdAndRestaurantId(Long userId, Long restaurantId);
    Optional<Favorite> findByUser_IdAndDishId(Long userId, Long dishId);

    // TODO Query para ids
}
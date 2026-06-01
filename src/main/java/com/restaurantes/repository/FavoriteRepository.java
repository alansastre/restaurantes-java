package com.restaurantes.repository;

import com.restaurantes.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_IdAndRestaurantIsNotNull(Long id);
    List<Favorite> findByUser_IdAndDishIsNotNull(Long id);

}
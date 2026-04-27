package com.restaurantes.repository;

import com.restaurantes.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRestaurant_IdOrderByCreationDateDesc(Long id);

    List<Review> findByRestaurant_IdAndRatingGreaterThanEqualOrderByCreationDateDesc(Long id, Integer rating);

    // rating >= 4 del restaurante 1 ordenados por más reciente primero
    // List<Review> findByRatingGreaterThanEqualAndRestaurant_IdOrderByCreationDateDesc(Integer rating, Long id);


}
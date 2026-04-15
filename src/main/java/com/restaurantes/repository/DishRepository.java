package com.restaurantes.repository;

import com.restaurantes.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByPriceLessThanEqual(Double price);

}
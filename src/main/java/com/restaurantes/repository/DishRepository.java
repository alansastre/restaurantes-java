package com.restaurantes.repository;

import com.restaurantes.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByPriceLessThanEqual(Double price);

    // Consulta para traer todos los platos de un restaurante por id,
    // ordenados por precio ascendente
    List<Dish> findByRestaurantIdOrderByPrice(Long id); // por defecto es ASCendente
    //List<Dish> findByRestaurantIdOrderByPriceAsc(Long id); // equivalente
}
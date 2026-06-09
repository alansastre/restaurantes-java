package com.restaurantes.controller.api;

import com.restaurantes.model.Dish;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DishRestController {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("dishes")
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }
    @GetMapping("dishes/{id}")
    public Dish findById(@PathVariable Long id) {
        return dishRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,  String.format("Dish with id %d not found", id)
                )
        );
    }
    @GetMapping("restaurants/{id}/dishes")
    public List<Dish> findAllByRestaurant(@PathVariable Long id) {
        return dishRepository.findByRestaurantIdOrderByPrice(id);
    }




}

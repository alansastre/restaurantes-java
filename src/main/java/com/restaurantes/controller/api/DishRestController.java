package com.restaurantes.controller.api;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
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

    @PostMapping("dishes")
    public ResponseEntity<Dish> create(@RequestBody Dish dish) {
        if (dish.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish ID must be null");
        }
        dish.setRestaurant(resolveRestaurant(dish));
        Dish saved = dishRepository.save(dish);
        return ResponseEntity.created(URI.create("/api/v1/dishes/" + saved.getId())).body(saved);
    }

    @PutMapping("dishes/{id}")
    public ResponseEntity<Dish> update(
            @PathVariable Long id,
            @RequestBody Dish dish
    ) {
        Dish existing = dishRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Dish " + id + " not found")
        );
        existing.setName(dish.getName());
        existing.setPrice(dish.getPrice());
        existing.setImageUrl(dish.getImageUrl());
        existing.setDescription(dish.getDescription());
        existing.setDishType(dish.getDishType());

        existing.setRestaurant(resolveRestaurant(dish)); // asociación

        return ResponseEntity.ok(dishRepository.save(existing));
    }

    @PatchMapping("dishes/{id}")
    public ResponseEntity<Dish> updatePartial(
            @PathVariable Long id,
            @RequestBody Dish dish
    ) {
        Dish existing = dishRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Dish " + id + " not found")
        );
        if (dish.getName() != null) existing.setName(dish.getName());
        if (dish.getPrice() != null) existing.setPrice(dish.getPrice());
        if (dish.getImageUrl() != null) existing.setImageUrl(dish.getImageUrl());
        if (dish.getDescription() != null) existing.setDescription(dish.getDescription());
        if (dish.getDishType() != null) existing.setDishType(dish.getDishType());
        if (dish.getRestaurant() != null && dish.getRestaurant().getId() != null) existing.setRestaurant(resolveRestaurant(dish)); // asociación

        return ResponseEntity.ok(dishRepository.save(existing));
    }


    @DeleteMapping("dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable Long id) {
        if(!dishRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish " + id + " not found");

        // si se quiere borrar completamente cuando hay asociaciones, primero hay que desasociar OrderLine y Review
        dishRepository.deleteById(id);
    }


    private Restaurant resolveRestaurant(Dish dish) {
        if (dish.getRestaurant() == null || dish.getRestaurant().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID can not be null");
        }
        return restaurantRepository.findById(dish.getRestaurant().getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,  String.format("Restaurant with id %d not found",
                        dish.getRestaurant().getId())
                )
        );
    }



}

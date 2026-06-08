package com.restaurantes.controller.api;

import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

// http://localhost:8080/swagger-ui/index.html
@RestController
@RequestMapping("/api/v1/restaurants")
@AllArgsConstructor
public class RestaurantRestController {

    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
    @GetMapping("{id}")
    public Restaurant findOne(@PathVariable Long id) {
        return restaurantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant " + id + " not found")
        );
    }
    // solo para crear nuevos restaurantes:
//    @PostMapping
//    public Restaurant save(@RequestBody Restaurant restaurant) {
//        restaurant.setId(null);
//        return restaurantRepository.save(restaurant);
//    }
//    @PostMapping
//    public ResponseEntity<Restaurant> save(@RequestBody Restaurant restaurant) {
//        restaurant.setId(null);
//        Restaurant saved = restaurantRepository.save(restaurant);
////        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//        return ResponseEntity.created(URI.create("/api/v1/restaurants/" + saved.getId())).body(saved);
//    }
    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        if (restaurant.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID must be null");
        }
        Restaurant saved = restaurantRepository.save(restaurant);
    //        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        return ResponseEntity.created(URI.create("/api/v1/restaurants/" + saved.getId())).body(saved);
    }

    // actualizar restaurante: actualización completa, si un campo se manda null también se guarda como null o default
    @PutMapping("{id}")
    public ResponseEntity<Restaurant> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant existing = restaurantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant " + id + " not found")
        );
        existing.setName(restaurant.getName());
        existing.setAveragePrice(restaurant.getAveragePrice());
        existing.setNumberEmployees(restaurant.getNumberEmployees());
        existing.setFoodType(restaurant.getFoodType());
        existing.setImageUrl(restaurant.getImageUrl());
        existing.setActive(restaurant.getActive());
        // como alternativa se podría usar DTOs y mappers
        // existing.setStartDate(restaurant.getStartDate()); // conservar fecha original

        return ResponseEntity.ok(restaurantRepository.save(existing));
    }

    // actualizar restaurante: actualización parcial, si viene a null no se toca
    // la diferencia es que PUT setea todos los campos, y PATCH solo los que llegaron no nulos
    @PatchMapping("{id}")
    public ResponseEntity<Restaurant> updatePartial(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant existing = restaurantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant " + id + " not found")
        );
        if(restaurant.getName() != null) existing.setName(restaurant.getName());
        if(restaurant.getAveragePrice() != null) existing.setAveragePrice(restaurant.getAveragePrice());
        if(restaurant.getNumberEmployees() != null) existing.setNumberEmployees(restaurant.getNumberEmployees());
        if(restaurant.getFoodType() != null) existing.setFoodType(restaurant.getFoodType());
        if(restaurant.getImageUrl() != null) existing.setImageUrl(restaurant.getImageUrl());

        return ResponseEntity.ok(restaurantRepository.save(existing));
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable Long id) {
        if(!restaurantRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant " + id + " not found");

        // hard delete: intenta borrar el restaurante completamente, pero fallará si hay asociaciones:
        try {
            restaurantRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant can't be deleted because it has relationships");
        }

        // soft delete: solo desactivarlo
        // findById  setActive false   save
    }


}

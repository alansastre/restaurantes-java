package com.restaurantes.controller.api;

import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

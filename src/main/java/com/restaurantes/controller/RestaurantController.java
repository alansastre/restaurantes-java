package com.restaurantes.controller;

import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.RestaurantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RestaurantController {

    // inyectar el restaurant repository
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // get all restaurants
    // http://localhost:8080/restaurants
    @GetMapping("restaurants") // controlador
    public String restaurantList(Model model) {
        // cargar datos en el modelo
        List<Restaurant> restaurants = restaurantRepository.findAll();
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("numRestaurants", restaurants.size());
        model.addAttribute("title", "Lista de restaurantes");
        return "restaurants/restaurant-list"; // vista
    }
}

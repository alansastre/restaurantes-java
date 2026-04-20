package com.restaurantes.controller;

import com.restaurantes.repository.RestaurantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestaurantController {

    // inyectar el restaurant repository
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // get all restaurants
    // http://localhost:8080/restaurants
    @GetMapping("restaurants")
    public String restaurantList(Model model) {
        model.addAttribute("restaurants",
                restaurantRepository.findAll());
        return "restaurants/restaurant-list";
    }
}

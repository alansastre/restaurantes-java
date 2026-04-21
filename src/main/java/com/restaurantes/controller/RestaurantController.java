package com.restaurantes.controller;

import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.RestaurantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class RestaurantController {

    // inyectar el restaurant repository
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /*
    Resumen de métodos típicos en una clase controller:
    @GetMapping("restaurants") findAll [OK]
    @GetMapping("restaurants/{id}") findById []
    @GetMapping("restaurants/delete/{id}") delete []



    @GetMapping("restaurants/create") createForm
    @PostMapping("restaurants/create") create

    @GetMapping("restaurants/{id}/edit") editForm
    @PostMapping("restaurants/{id}/edit") edit
     */

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

    // nuevo metodo para traer un solo restaurante por su id
    @GetMapping("restaurants/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {

        // buscar restaurante por su id: findById
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if (restaurantOptional.isPresent()) {

            // El restaurante sí existe
            Restaurant restaurant = restaurantOptional.get();
            model.addAttribute("restaurant", restaurant);
            return "restaurants/restaurant-detail";

        }

        // El restaurante NO existe
        // CUIDADO no apunta a HTML
        // APUNTA al Controller
        return "redirect:/restaurants";
    }
}

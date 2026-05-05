package com.restaurantes.controller;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Restaurant;
import com.restaurantes.model.Review;
import com.restaurantes.model.enums.FoodType;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import com.restaurantes.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

// PRoductController
@Controller
@AllArgsConstructor // lombok
public class RestaurantController {

    // inyectar el restaurant repository
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final ReviewRepository reviewRepository;

//    // http://localhost:8080/restaurants
//    @GetMapping("restaurants") // controlador
//    public String restaurantList(Model model) {
//        // cargar datos en el modelo
////        List<Restaurant> restaurants = restaurantRepository.findAll();
//        List<Restaurant> restaurants = restaurantRepository.findByActiveTrue();
//        model.addAttribute("restaurants", restaurants);
//        model.addAttribute("numRestaurants", restaurants.size());
//        model.addAttribute("title", "Lista de restaurantes");
//        return "restaurants/restaurant-list"; // vista
//    }
    // http://localhost:8080/restaurants?foodType=SPANISH
    // http://localhost:8080/restaurants?price=15
    // http://localhost:8080/restaurants?title=burguer
    @GetMapping("restaurants") // controlador
    public String restaurantList(
            Model model,
            @RequestParam(required = false) FoodType foodType,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String title
    ) {
        List<Restaurant> restaurants = restaurantRepository.findActiveFiltering(foodType, price, title);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("numRestaurants", restaurants.size());
        model.addAttribute("title", "Lista de restaurantes");
        return "restaurants/restaurant-list"; // vista
    }

    // nuevo metodo para traer un solo restaurante por su id
    @GetMapping("restaurants/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {


        // buscar restaurante por su id: findById
//        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByIdAndActiveTrue(id);
        if (restaurantOptional.isPresent()) {


            // El restaurante sí existe
            Restaurant restaurant = restaurantOptional.get();
            model.addAttribute("restaurant", restaurant);
            // opcional:
            // cargar los platos (Dish) de este restaurant en el model
            List<Dish> platos = dishRepository.findByRestaurantIdOrderByPrice(restaurant.getId());
            model.addAttribute("dishes", platos);

            // reviews
            //List<Review> reviews = reviewRepository.findAll();
            List<Review> reviews = reviewRepository.findByRestaurant_IdOrderByCreationDateDesc(restaurant.getId());
            model.addAttribute("reviews", reviews); // accesibles desde HTML

            return "restaurants/restaurant-detail";

        }

        // El restaurante NO existe
        // CUIDADO no apunta a HTML
        // APUNTA al Controller
        return "redirect:/restaurants";
    }

    @GetMapping("restaurants/deactivate/{id}")
    public String restaurantDeactivate(@PathVariable Long id, Model model) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if(restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            restaurant.setActive(false);
            restaurantRepository.save(restaurant);
        }

        return "redirect:/restaurants";
    }

    // ruta para entrar al formulario de restaurante
    @GetMapping("restaurants/new")
    public String newRestaurants(Model model) {
        return "restaurants/restaurant-form";
    }
}

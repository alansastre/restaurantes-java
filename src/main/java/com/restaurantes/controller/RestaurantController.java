package com.restaurantes.controller;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Restaurant;
import com.restaurantes.model.Review;
import com.restaurantes.model.User;
import com.restaurantes.model.enums.FoodType;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import com.restaurantes.repository.ReviewRepository;
import com.restaurantes.service.FavoriteService;
import com.restaurantes.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final FavoriteService favoriteService;
    private final FileService fileService;

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
            @RequestParam(required = false) String title,
            @AuthenticationPrincipal User user
    ) {
        List<Restaurant> restaurants = restaurantRepository.findActiveFiltering(foodType, price, title);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("numRestaurants", restaurants.size());
        model.addAttribute("title", "Lista de restaurantes");
//        if(user != null) {
//            model.addAttribute("favoriteRestaurantIds",
//                    favoriteService.findRestaurantIdsByUserId(user.getId()));
//        }
        return "restaurants/restaurant-list"; // vista
    }

    @GetMapping("restaurants/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByIdAndActiveTrue(id);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            model.addAttribute("restaurant", restaurant);
            List<Dish> platos = dishRepository.findByRestaurantIdOrderByPrice(restaurant.getId());
            model.addAttribute("dishes", platos);
            List<Review> reviews = reviewRepository.findByRestaurant_IdOrderByCreationDateDesc(restaurant.getId());
            model.addAttribute("reviews", reviews);
//            if(user != null) {
//                model.addAttribute("favoriteRestaurantIds",
//                        favoriteService.findRestaurantIdsByUserId(user.getId()));
//            }
            return "restaurants/restaurant-detail";
        }
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

    // CREAR UN NUEVO RESTAURANTE DESDE CERO
    @GetMapping("restaurants/new")
    public String newRestaurant(Model model) {
        // añadir objeto Restaurant vacío para rellenarlo desde el formulario
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("foodTypes", FoodType.values());
        return "restaurants/restaurant-form";
    }

    // EDITAR UN RESTAURANTE EXISTENTE
    @GetMapping("restaurants/edit/{id}")
    public String editRestaurant(@PathVariable Long id, Model model) {
        model.addAttribute("restaurant", restaurantRepository.findById(id).orElseThrow());
        model.addAttribute("foodTypes", FoodType.values());
        return "restaurants/restaurant-form";
    }


    @PostMapping("restaurants")
    public String createRestaurant(
        @ModelAttribute Restaurant restaurant,
        @RequestParam("imageFile") MultipartFile imageFile
    ) {

        String imageUrl = fileService.store(imageFile);
        if (imageUrl != null)
            restaurant.setImageUrl(imageUrl);

        restaurantRepository.save(restaurant);
        return "redirect:/restaurants/" + restaurant.getId();
    }


}

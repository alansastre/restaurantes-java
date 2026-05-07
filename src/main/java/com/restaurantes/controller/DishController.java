package com.restaurantes.controller;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Review;
import com.restaurantes.model.enums.DishType;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import com.restaurantes.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DishController {

    private final DishRepository dishRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("dishes")
    public String listDishes(Model model) {
        List<Dish> dishes = dishRepository.findAll();
        model.addAttribute("dishes", dishes);
        return "dishes/dish-list";
    }

    @GetMapping("dishes/{id}")
    public String dishDetail(@PathVariable Long id, Model model) {

        Optional<Dish> dishOptional = dishRepository.findById(id);

        if (dishOptional.isPresent()) {
            Dish dish = dishOptional.get();
            model.addAttribute("dish", dish);
            // traer reviews de este plato y cargar en model
            List<Review> reviews = reviewRepository.findByDish_IdOrderByCreationDateDesc(id);
            model.addAttribute("reviews", reviews);
            return "dishes/dish-detail";
        }

        return "redirect:/restaurants";
    }

    // GET newDish
    @GetMapping("dishes/new")
    public String newDish(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("dishTypes", DishType.values());
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "dishes/dish-form";
    }
    // GET editDish
    @GetMapping("dishes/edit/{id}")
    public String editDish(@PathVariable Long id, Model model) {
        model.addAttribute("dish", dishRepository.findById(id).orElseThrow());
        model.addAttribute("dishTypes", DishType.values());
        model.addAttribute("restaurants", restaurantRepository.findAll());
        return "dishes/dish-form";
    }

    // POST saveDish
    @PostMapping("dishes")
    public String saveDish(@ModelAttribute Dish dish){
        dishRepository.save(dish);
        return "redirect:/dishes";
    }
}

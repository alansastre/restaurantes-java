package com.restaurantes.controller;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Review;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class DishController {

    private final DishRepository dishRepository;
    private final ReviewRepository reviewRepository;


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
}

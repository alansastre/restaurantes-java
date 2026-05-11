package com.restaurantes.controller;

import com.restaurantes.model.Review;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import com.restaurantes.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ReviewController {

    // inyectar el repositorio de reviews
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    // getmapping reviews
    @GetMapping("reviews")
    public String reviews(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "reviews/review-list";
    }

    @GetMapping("reviews/{id}")
    public String review(Model model, @PathVariable Long id) {
        model.addAttribute("review",  reviewRepository.findById(id).orElseThrow());
        return "reviews/review-detail";
    }

    @GetMapping("reviews/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Borrado exitosamente");
        return "redirect:/reviews";
    }

    @GetMapping("reviews/disable/{id}")
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
//        if (reviewOpt.isPresent()) {
//            Review review = reviewOpt.get();
//            review.setActive(false);
//            reviewRepository.save(review);
//        }
        redirectAttributes.addFlashAttribute("message", "Desactivado exitosamente");
        return "redirect:/reviews";
    }

    // @GetMapping reviews/new reviews-form.html
    // restaurantId
    // dishId
    @GetMapping("reviews/new")
    public String newReview(
            Model model,
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Long dishId) {
        Review review = new Review();

        if (restaurantId != null)
            review.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow());

        if (dishId != null)
            review.setDish(dishRepository.findById(dishId).orElseThrow());

        model.addAttribute("review", review);
        return "reviews/review-form";
    }


    // @PostMapping reviews
    // save
    // redirect




}

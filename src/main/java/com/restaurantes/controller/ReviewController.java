package com.restaurantes.controller;

import com.restaurantes.model.Review;
import com.restaurantes.model.User;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.RestaurantRepository;
import com.restaurantes.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        model.addAttribute("review",
                reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay review")));
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


    // Get Mapping reviews / edit / {id}
    @GetMapping("reviews/edit/{id}")
    public String editReview(Model model, @PathVariable Long id) {
        model.addAttribute("review", reviewRepository.findById(id).orElseThrow());
        return "reviews/review-form";
    }


    // @PostMapping reviews con Spring Validation @Valid
    @PostMapping("reviews")
    public String saveReview(
            @Valid @ModelAttribute Review review,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user) {


        if (bindingResult.hasErrors()) {
            return "reviews/review-form";
        }

        review.setUser(user);
        reviewRepository.save(review);

        if (review.getRestaurant() != null)
            return "redirect:/restaurants/" + review.getRestaurant().getId();

        if (review.getDish() != null)
            return "redirect:/dishes/" + review.getDish().getId();

        return "redirect:/reviews";
    }



}

package com.restaurantes.controller;

import com.restaurantes.model.User;
import com.restaurantes.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // toggle para marcar o desmarcar como favorito
    @PostMapping("favorites/toggle")
    public String toggle(
            @RequestParam String type,
            @RequestParam Long targetId,
            @RequestParam(defaultValue = "/restaurants") String redirectUrl,
            @AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes
    ) {

        boolean favorited;
        if(type.equalsIgnoreCase("restaurant")) {
            favorited = favoriteService.toggleRestaurant(user, targetId);
        }
//        else if(type.equalsIgnoreCase("dish")) {
//            favorited = favoriteService.toggleDish(user, targetId);
//        }
        else {
            return  "redirect:" + redirectUrl;
        }

        if(favorited) {
            redirectAttributes.addFlashAttribute("message", "añadido como favorito");
        } else {
            redirectAttributes.addFlashAttribute("message", "quitado como favorito");
        }
        return  "redirect:" + redirectUrl;
    }
}

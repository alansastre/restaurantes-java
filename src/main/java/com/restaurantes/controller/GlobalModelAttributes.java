package com.restaurantes.controller;

import com.restaurantes.model.User;
import com.restaurantes.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

/*
Advice para exponer información global accesible desde cualquier
HTML para evitar repetirla en todos los controladores
 */
@ControllerAdvice(annotations = Controller.class)
@AllArgsConstructor
public class GlobalModelAttributes {

    private final FavoriteService  favoriteService;

    @ModelAttribute("favoriteRestaurantIds")
    public Set<Long> getFavoriteRestaurantIds(@AuthenticationPrincipal User user) {
        if(user != null) {
            return favoriteService.findRestaurantIdsByUserId(user.getId());
        }
        return Set.of();
    }
}

package com.restaurantes.controller;

import com.restaurantes.model.User;
import com.restaurantes.service.FavoriteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/*
Advice para exponer información global accesible desde cualquier
HTML para evitar repetirla en todos los controladores
 */
@Slf4j
@ControllerAdvice(annotations = Controller.class)
@AllArgsConstructor
public class GlobalModelAttributes {

    private final FavoriteService  favoriteService;
    private final Environment env;

    @ModelAttribute("favoriteRestaurantIds")
    public Set<Long> getFavoriteRestaurantIds(@AuthenticationPrincipal User user) {
        if(user != null) {
            return favoriteService.findRestaurantIdsByUserId(user.getId());
        }
        return Set.of();
    }

    @ModelAttribute("favoriteDishIds")
    public Set<Long> getFavoriteDishIds(@AuthenticationPrincipal User user) {
        if(user != null) {
            return favoriteService.findDishIdsByUserId(user.getId());
        }
        return Set.of();
    }

    @ModelAttribute("env")
    public String getEnv() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        log.info("ACTIVE PROFILES: {}", profiles);
        return !profiles.isEmpty() ? profiles.getFirst() : "dev";
    }
}

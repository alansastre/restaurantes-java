package com.restaurantes.controller;

import com.restaurantes.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // toggle para marcar o desmarcar como favorito
}

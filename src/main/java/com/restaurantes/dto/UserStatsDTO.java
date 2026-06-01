package com.restaurantes.dto;

import com.restaurantes.model.Favorite;
import com.restaurantes.model.Order;
import com.restaurantes.model.Review;

import java.util.List;

// Información del usuario que no está en la tabla usuario porque
// está en sus asociaciones
// a futuro se puede añadir aquí más info del usuario:
// por ejemplo: favoritos, puntos de fidelización, ...
public record UserStatsDTO(
        long countReviews,
        List<Review> reviews,
        long countOrders,
        List<Order> orders,
        double moneySpent,
        List<Favorite> favoriteRestaurants,
        List<Favorite> favoriteDishes
) { }

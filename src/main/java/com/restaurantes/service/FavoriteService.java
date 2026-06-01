package com.restaurantes.service;

import com.restaurantes.model.Favorite;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.FavoriteRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RestaurantRepository  restaurantRepository;
    private final DishRepository  dishRepository;

    public List<Favorite> findFavoriteRestaurants(Long userId) {
        return favoriteRepository.findByUser_IdAndRestaurantIsNotNull(userId);
    }

    public List<Favorite> findFavoriteDishes(Long userId) {
        return favoriteRepository.findByUser_IdAndDishIsNotNull(userId);
    }
}

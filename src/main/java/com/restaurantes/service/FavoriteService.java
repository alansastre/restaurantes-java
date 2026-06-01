package com.restaurantes.service;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Favorite;
import com.restaurantes.model.Restaurant;
import com.restaurantes.model.User;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.FavoriteRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Set<Long> findRestaurantIdsByUserId(Long userId) {
        return favoriteRepository.findRestaurantIdsByUserId(userId);
    }


    public boolean toggleRestaurant(User user, Long restaurantId) {

        Optional<Favorite> existing = favoriteRepository
                .findByUser_IdAndRestaurantId(user.getId(), restaurantId);

        // Opción 1: es que ya existe y lo quitamos de favorito:
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return false;
        }

        // Opción 2: no existe y lo creamos como favorito
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        favoriteRepository.save(Favorite.builder().restaurant(restaurant).user(user).build());
        return true;
    }

    public boolean toggleDish(User user, Long dishId) {
        Optional<Favorite> existing = favoriteRepository.findByUser_IdAndDishId(user.getId(), dishId);
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return false;
        }

        Dish dish = dishRepository.findById(dishId).orElseThrow();
        favoriteRepository.save(Favorite.builder().dish(dish).user(user).build());
        return true;
    }
}

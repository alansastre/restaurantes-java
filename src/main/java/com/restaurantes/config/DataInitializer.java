package com.restaurantes.config;

import com.restaurantes.dto.RegisterForm;
import com.restaurantes.model.Dish;
import com.restaurantes.model.Favorite;
import com.restaurantes.model.Restaurant;
import com.restaurantes.model.User;
import com.restaurantes.model.enums.DishType;
import com.restaurantes.model.enums.FoodType;
import com.restaurantes.model.enums.Role;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.FavoriteRepository;
import com.restaurantes.repository.RestaurantRepository;
import com.restaurantes.repository.UserRepository;
import com.restaurantes.service.FavoriteService;
import com.restaurantes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
Clase para insertar datos demo en el entorno de desarrollo
 */
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        // opcion usando el service:
        User user = userService.register(RegisterForm.builder()
                .username("user")
                .email("user@gmail.com")
                .password("user")
                .passwordConfirm("user")
                .build());

        // opcion usando directamente repository
        User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ROLE_ADMIN)
                .active(true)
                .imageUrl("/uploads/ollama.png")
                .build());


        // Crear un restaurante  y un plato

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurant para fav");
        restaurant.setAveragePrice(20d);
        restaurant.setNumberEmployees(2);
        restaurant.setFoodType(FoodType.SPANISH);
        restaurantRepository.save(restaurant);

        Dish dish = new Dish();
        dish.setName("Dish para fav");
        dish.setPrice(10.5d);
        dish.setDescription("Plato para darle a favoritos");
        dish.setDishType(DishType.DESSERT);
        dishRepository.save(dish);

        favoriteRepository.save(Favorite.builder().dish(dish).user(user).build());
        favoriteRepository.save(Favorite.builder().restaurant(restaurant).user(user).build());

        // guardarlos como favoritos de un usuario
        // así los podemos mostrar en el user-detail.html
    }
}

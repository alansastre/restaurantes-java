package com.restaurantes.config;

import com.restaurantes.dto.RegisterForm;
import com.restaurantes.model.*;
import com.restaurantes.model.enums.DishType;
import com.restaurantes.model.enums.FoodType;
import com.restaurantes.model.enums.OrderStatus;
import com.restaurantes.model.enums.Role;
import com.restaurantes.repository.*;
import com.restaurantes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Carga datos de demo al arrancar la aplicacion.
 *
 * IDEMPOTENTE: si la base de datos ya tiene datos, no vuelve a sembrar. Asi, con el perfil
 * prod (PostgreSQL persistente) la app se puede arrancar y reiniciar sin duplicar datos ni
 * petar por restricciones UNIQUE. Con el perfil dev (H2 create-drop) la BD esta vacia en
 * cada arranque, por lo que siembra siempre.
 */
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Idempotencia: si ya hay datos no volver a sembrar (ver javadoc de la clase).
        if (userRepository.count() > 0) return;

        // ===== Usuarios =====
        // 'user' por el servicio (cifra la contrasena y aplica las reglas de registro);
        // 'admin' directo por el repositorio para asignarle ROLE_ADMIN.
        User user = userService.register(RegisterForm.builder()
                .username("user")
                .email("user@gmail.com")
                .password("user")
                .passwordConfirm("user")
                .build());

        User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ROLE_ADMIN)
                .active(true)
                .imageUrl("/uploads/ollama.png")
                .build());

        // ===== Restaurantes ===== (ojo: 'name' es UNIQUE -> nombres distintos)
        Restaurant taberna = new Restaurant("La Taberna de Pepe", 22.5, 8);
        taberna.setFoodType(FoodType.SPANISH);
        taberna.setImageUrl("/uploads/restaurant-placeholder.png");

        Restaurant sakura = new Restaurant("Sakura Sushi", 30.0, 5);
        sakura.setFoodType(FoodType.JAPANESE);
        sakura.setImageUrl("/uploads/restaurant-placeholder.png");

        Restaurant montaditos = new Restaurant("100 Montaditos", 12.0, 6);
        montaditos.setFoodType(FoodType.SPANISH);

        Restaurant osaka = new Restaurant("Restaurante Osaka", 18.0, 4);
        osaka.setFoodType(FoodType.JAPANESE);
        osaka.setActive(false); // uno inactivo, para ver el borrado logico en el listado

        restaurantRepository.saveAll(List.of(taberna, sakura, montaditos, osaka));

        // ===== Platos =====
        Dish ensalada = new Dish(null, "Ensalada de la casa", "Lechuga, tomate y atun", 6.5, DishType.STARTER, taberna);
        Dish lentejas = new Dish(null, "Lentejas con chorizo", "Receta tradicional", 8.0, DishType.MAIN, taberna);
        Dish tarta = new Dish(null, "Tarta de queso", "Con mermelada de frutos rojos", 5.5, DishType.DESSERT, taberna);
        Dish ramen = new Dish(null, "Ramen tonkotsu", "Caldo de cerdo cocinado 12h", 13.5, DishType.MAIN, sakura);
        Dish gyozas = new Dish(null, "Gyozas (6 ud)", "Empanadillas japonesas de cerdo", 6.0, DishType.STARTER, sakura);
        Dish mochi = new Dish(null, "Mochi de matcha", "Postre tradicional", 4.5, DishType.DESSERT, sakura);
        Dish bravas = new Dish(null, "Patatas bravas", "Con salsa picante de la casa", 5.0, DishType.STARTER, montaditos);
        dishRepository.saveAll(List.of(ensalada, lentejas, tarta, ramen, gyozas, mochi, bravas));

        // ===== Empleados (asociados a su restaurante) =====
        Employee pepe = new Employee("Pepe", "Garcia", 45, "11111111A");
        pepe.setRestaurant(taberna);
        Employee lucia = new Employee("Lucia", "Martin", 31, "22222222B");
        lucia.setRestaurant(taberna);
        Employee ken = new Employee("Ken", "Watanabe", 38, "33333333C");
        ken.setRestaurant(sakura);
        employeeRepository.saveAll(List.of(pepe, lucia, ken));

        // ===== Resenas ===== (title, description y rating 1-5 son obligatorios)
        reviewRepository.saveAll(List.of(
                Review.builder().title("Espectacular").description("Comida casera de diez, repetire seguro").rating(5).restaurant(taberna).user(user).build(),
                Review.builder().title("Correcto").description("Bien de precio, raciones algo justas").rating(3).restaurant(taberna).build(),
                Review.builder().title("El mejor ramen").description("El caldo esta increible, muy recomendable").rating(5).restaurant(sakura).user(user).build(),
                Review.builder().title("Postre top").description("La tarta de queso estaba buenisima").rating(4).dish(tarta).user(user).build()
        ));

        // ===== Un pedido con sus lineas =====
        Order pedido = new Order();
        pedido.setNumPeople(2);
        pedido.setTableNumber(5);
        pedido.setRestaurant(taberna);
        pedido.setUser(user);
        pedido.setStatus(OrderStatus.FINISHED);
        orderRepository.save(pedido);

        orderLineRepository.saveAll(List.of(
                new OrderLine(1, pedido, ensalada),
                new OrderLine(2, pedido, lentejas),
                new OrderLine(1, pedido, tarta)
        ));

        // total = 6.5 + 2*8.0 + 5.5 = 28.0
        double total = ensalada.getPrice() + 2 * lentejas.getPrice() + tarta.getPrice();
        pedido.setTotalPrice(total);
        orderRepository.save(pedido);

        // ===== Favoritos del usuario 'user' =====
        favoriteRepository.saveAll(List.of(
                Favorite.builder().user(user).restaurant(sakura).build(),
                Favorite.builder().user(user).dish(ramen).build()
        ));
    }
}

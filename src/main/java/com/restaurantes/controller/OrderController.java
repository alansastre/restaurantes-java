package com.restaurantes.controller;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Order;
import com.restaurantes.model.Restaurant;
import com.restaurantes.model.enums.OrderStatus;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.OrderLineRepository;
import com.restaurantes.repository.OrderRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    // @GetMapping orders
    // filtrar por restaurante, filtrar por usuario
    @GetMapping("orders")
    public String orders(Model model) {
        model.addAttribute("orders",  orderRepository.findAll());
        return "orders/order-list";
    }

    // @GetMapping orders/{id}
    @GetMapping("orders/{id}")
    public String order(Model model, @PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow();
        model.addAttribute("order", order);
        model.addAttribute("orderLines", orderLineRepository.findByOrder_Id(id));
        // cargar platos para que el usuario pueda seleccionarlos para el pedido
        List<Dish> dishes = dishRepository.findByRestaurantIdOrderByPrice(order.getRestaurant().getId());
        model.addAttribute("dishes", dishes);
        return "orders/order-detail";
    }

    @GetMapping("orders/new")
    public String newOrder(Model model, @RequestParam Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        Order order = new Order();
        order.setRestaurant(restaurant);
        model.addAttribute("order", order);
        return "orders/order-form";
    }


    @PostMapping("orders")
    public String save(@ModelAttribute Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setDate(LocalDateTime.now());
        order.setTotalPrice(0d);
        orderRepository.save(order);
        return "redirect:/orders/" + order.getId();
    }

    // TODO @PostMapping("orders/{id}/lines")    dishId=1


}

package com.restaurantes.controller;

import com.restaurantes.model.Order;
import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.OrderLineRepository;
import com.restaurantes.repository.OrderRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final RestaurantRepository restaurantRepository;

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
        model.addAttribute("order", orderRepository.findById(id).orElseThrow());
        model.addAttribute("orderLines", orderLineRepository.findByOrder_Id(id));
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

    // TODO @PostMapping /orders

    // TODO ....
}

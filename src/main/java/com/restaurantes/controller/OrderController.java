package com.restaurantes.controller;

import com.restaurantes.model.Dish;
import com.restaurantes.model.Order;
import com.restaurantes.model.OrderLine;
import com.restaurantes.model.Restaurant;
import com.restaurantes.model.enums.OrderStatus;
import com.restaurantes.repository.DishRepository;
import com.restaurantes.repository.OrderLineRepository;
import com.restaurantes.repository.OrderRepository;
import com.restaurantes.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("orders/{id}/lines")
    public String addLineDish(
            @PathVariable Long id, @RequestParam Long dishId) {

        Order order = orderRepository.findById(id).orElseThrow();
        Dish dish = dishRepository.findById(dishId).orElseThrow();

        Optional<OrderLine> lineOptional =  orderLineRepository.findByOrder_IdAndDish_Id(id, dishId);

        // opción imperativa clásica tradicional
        OrderLine orderLine;
        if (lineOptional.isPresent()) {
            orderLine = lineOptional.get();
            orderLine.setQuantity(orderLine.getQuantity() + 1);
        } else {
            orderLine = new OrderLine();
            orderLine.setDish(dish);
            orderLine.setOrder(order);
            orderLine.setQuantity(1);
        }
        orderLineRepository.save(orderLine);
        // opción alternativa estilo funcional
//        OrderLine line = orderLineRepository
//                .findByOrder_IdAndDish_Id(id, dishId)
//                .orElseGet(() -> new OrderLine(0, order, dish));
//
//        line.setQuantity(line.getQuantity() + 1);
//        orderLineRepository.save(line);

        if (order.getStatus() == OrderStatus.PENDING)
            order.setStatus(OrderStatus.IN_PROGRESS);

        Double totalPrice = orderLineRepository.calculateTotalPrice(order.getId());
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        return "redirect:/orders/" + order.getId();
    }

    @GetMapping("orders/{id}/finish")
    public String finish(@PathVariable Long id) {
        Order order =  orderRepository.findById(id).orElseThrow();
        order.setStatus(OrderStatus.FINISHED);
        order.setTotalPrice(orderLineRepository.calculateTotalPrice(order.getId()));
        // tip, iva, service charge, terrace
        orderRepository.save(order);
        return "redirect:/orders/" + id;
    }

    @GetMapping("orders/{orderId}/lines/{lineId}/delete")
    public String deleteLine(@PathVariable Long orderId, @PathVariable Long lineId) {
        orderLineRepository.deleteById(lineId);
        Order order =  orderRepository.findById(orderId).orElseThrow();
        order.setTotalPrice(orderLineRepository.calculateTotalPrice(order.getId()));
        orderRepository.save(order);
        return "redirect:/orders/" + orderId;
    }

    // @PostMapping
    // /orders/{orderId}/lines/{lineId}?quantity=5
    // RquestParam quantity
    @PostMapping("orders/{orderId}/lines/{lineId}")
    public String updateLineQuantity(
            @PathVariable Long orderId,
            @PathVariable Long lineId,
            @RequestParam Integer quantity) {

        if (quantity >= 1) {
            OrderLine orderLine = orderLineRepository.findById(lineId).orElseThrow();
            orderLine.setQuantity(quantity);
            orderLineRepository.save(orderLine);

            Order order =  orderRepository.findById(orderId).orElseThrow();
            order.setTotalPrice(orderLineRepository.calculateTotalPrice(order.getId()));
            orderRepository.save(order);
        }

        return "redirect:/orders/" + orderId;
    }
}

package com.restaurantes.controller;

import com.restaurantes.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("products/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/product-detail";
    }

    @GetMapping("products")
    public String findAll(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/product-list";
    }
}

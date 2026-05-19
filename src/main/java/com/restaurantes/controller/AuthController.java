package com.restaurantes.controller;

import com.restaurantes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;


    // @GetMapping /register

    // @PostMapping /register

    // @GetMapping  /login





    // NO hace falta
    // @PostMapping /login   porque Spring Security lo hace automaticamente
    // @PostMapping /logout   Spring security lo hace automatico
}

package com.restaurantes.controller;

import com.restaurantes.dto.RegisterForm;
import com.restaurantes.model.User;
import com.restaurantes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    // Navegar a formulario de registro
    // @GetMapping /register
    @GetMapping("register")
    public String register(Model model) {
        // opcion 1: entidad User
        // model.addAttribute("user", new User());
        // opcion 2: dto UserRegisterDTO
        model.addAttribute("user", new RegisterForm()); // UserRegisterDTO o RegisterForm
        return "auth/register";
    }

    // @PostMapping /register
    @PostMapping("register")
    public String register(@ModelAttribute RegisterForm form){
        System.out.println(form);
        // verificar si username ocupado
        // verificar si email ocupado
        // verificar password
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }
    // NO hace falta
    // @PostMapping /login   porque Spring Security lo hace automaticamente
    // @PostMapping /logout   Spring security lo hace automatico
}

package com.restaurantes.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HelloController {


    // HTTP GET
    // http://localhost:8080/hola
    @GetMapping("hola")
    public String hola() {
        return "hola";
    }

    @GetMapping("adios")
    public String adios(Model model){
        model.addAttribute("mensaje", "Hasta luego máquinas");
        return "adios";
    }

}

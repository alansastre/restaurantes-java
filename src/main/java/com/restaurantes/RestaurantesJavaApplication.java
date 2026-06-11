package com.restaurantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Una clase con @Entity equivale a una tabla de base de datos.
// Un objeto equivale a una fila en una tabla de base de datos.
// Los datos de demo se cargan al arrancar desde config/DataInitializer.
@SpringBootApplication
public class RestaurantesJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantesJavaApplication.class, args);
    }

}

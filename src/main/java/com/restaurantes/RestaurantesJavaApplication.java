package com.restaurantes;

import com.restaurantes.model.Employee;
import com.restaurantes.model.Restaurant;
import com.restaurantes.repository.EmployeeRepository;
import com.restaurantes.repository.RestaurantRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

// Una clase con @Entity equivale a una tabla de base de datos
// Un objeto equivale a una fila en una tabla de base de datos

@SpringBootApplication
public class RestaurantesJavaApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(RestaurantesJavaApplication.class, args);

        // obtener los repositorios para poder hacer operaciones de base de datos con ellos
        RestaurantRepository restaurantRepository = context.getBean(RestaurantRepository.class);
        EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
        // crear un objeto restaurante: new
        Restaurant nuevoRestaurante = new Restaurant();
        nuevoRestaurante.setName("Paco Bar");
        nuevoRestaurante.setAveragePrice(20.33);
        nuevoRestaurante.setNumberEmployees(5);

        // guardar el restaurante en base de datos usando el repositorio: .save()
        restaurantRepository.save(nuevoRestaurante);


        // Opción 1: crear un objeto vacío:
        Restaurant rest = new Restaurant();

        // Opción 2: crear un objeto con datos:
        Restaurant rest2 = new Restaurant("100 montaditos", 22.22, 5);
        restaurantRepository.save(rest2);

        // Opción 3: crearlo vacío y actualizarlo con métodos set
        Restaurant rest3 = new Restaurant();
        rest3.setName("Rest3");
        rest3.setAveragePrice(50.22);
        restaurantRepository.save(rest3);


        // Crear un empleado y guardarlo en base datos
        Employee emp1 = new Employee();
        employeeRepository.save(emp1);

        Employee emp2 = new Employee("Pepe", "García", 30, "7776645R");
        employeeRepository.save(emp2);

        System.out.println(emp1);
        System.out.println(emp2);

        // obtener todos los restaurantes de base de datos
        // SELECT * from restaurantes;
        List<Restaurant> restaurantes = restaurantRepository.findAll();
        // System.out.println(restaurantes);
//        for (int i = 0; i < restaurantes.size(); i++) { // size() nos dice el número de elementos en la lista
//            System.out.println(restaurantes.get(i));
//        }
        for (Restaurant restaurant : restaurantes) { // bucle foreach itera uno a uno los restaurantes sin crear un index
            System.out.println(restaurant);
        }


        List<Employee> empleados = employeeRepository.findAll();
        // System.out.println(empleados);
        for (Employee empleado : empleados) {
            System.out.println(empleado);
        }


        // findById traer un restaurante/empleado

        // deleteAll

        // deleteById


    }

}








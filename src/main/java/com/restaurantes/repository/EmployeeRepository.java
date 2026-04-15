package com.restaurantes.repository;

import com.restaurantes.model.Employee;
import com.restaurantes.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    // Métodos de consulta derivados (derived queries) basados en el nombre del metodo

    // SPRING DATA JPA -- > HIBERNATE (JPA) --> JDBC --> H2/ MYSQL / POSTGRESQL
    List<Employee> findByDni(String dni);

    List<Employee> findByAge(Integer age);

    List<Employee> findByRestaurantName(String dominosPizza);

    List<Employee> findByLastName(String lastName);


    List<Employee> findByFirstName(String name);

    List<Employee> findByRestaurant_FoodType(FoodType foodType);

    List<Employee> findByRestaurantAveragePrice(Double averagePrice);

    List<Employee> findByAgeGreaterThanEqual(Integer age);


}

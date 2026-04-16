package com.restaurantes.repository;

import com.restaurantes.model.Employee;
import com.restaurantes.model.enums.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    // Este metodo no está filtrando, solo está ordenando
    @Query("select e from Employee e order by e.firstName")
    List<Employee> findByOrderByFirstNameAsc();
    // OTRAS FORMAS DE ORDENAR TÍPICAS SERÍAN ORDENAR POR PRECIO ASC EN PRODUCTOS


}

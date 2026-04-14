package com.restaurantes.model;

import jakarta.persistence.*;

/*
 SELECT

e.id, e.first_name,
r.id, r.name

FROM employees e
JOIN restaurantes r ON e.restaurant_id = r.id;
 */
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String dni;

    // asociación entre empleado y restaurante
//     @ManyToOne
//     private Restaurant restaurant;


    @ManyToOne
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // constructor con parámetros
    public Employee(String firstName, String lastName, Integer age, String dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dni = dni;
    }

    // constructor vacío
    public Employee() {}

    // getter y setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", dni='" + dni + '\'' +

                // SI EL RESTAURANTE ES NULL ENTONCES HACE NULL.getId() Y DA ERROR NULL POINTER EXCEPTION
               // ", restaurant=" + restaurant.getId() +
                    ", restaurant=" + (restaurant != null ? restaurant.getId() : null) +
                '}';
    }
}
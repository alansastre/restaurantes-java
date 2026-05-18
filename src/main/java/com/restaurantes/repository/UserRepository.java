package com.restaurantes.repository;

import com.restaurantes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // REGISTRO: verificar si email o username están ocupados
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    // LOGIN: recuperar el user
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
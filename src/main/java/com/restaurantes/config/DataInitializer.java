package com.restaurantes.config;

import com.restaurantes.dto.RegisterForm;
import com.restaurantes.model.User;
import com.restaurantes.model.enums.Role;
import com.restaurantes.repository.UserRepository;
import com.restaurantes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
Clase para insertar datos demo en el entorno de desarrollo
 */
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        // opcion usando el service:
        User user = userService.register(RegisterForm.builder()
                .username("user")
                .email("user@gmail.com")
                .password("user")
                .passwordConfirm("user")
                .build());

        // opcion usando directamente repository
        User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ROLE_ADMIN)
                .build());

    }
}

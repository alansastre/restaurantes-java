package com.restaurantes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // passwordEncoder para cifrar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // securityFilterChain para proteger acceso a rutas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // h2 usa iframes

        http.authorizeHttpRequests(
                auth -> auth
                // ORDEN IMPORTANTE
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/hola", "/adios", "/login", "/register",
                        "/uploads/**",
                        "/css/**", "/images/**", "/webjars/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/restaurants").permitAll()
                .requestMatchers(HttpMethod.POST, "/restaurants").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/deactivate/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/new").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/edit/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/*").permitAll()

                .requestMatchers(HttpMethod.GET, "/dishes").permitAll()
                .requestMatchers(HttpMethod.POST, "/dishes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/dishes/new").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/dishes/edit/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/dishes/*").permitAll()

                .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                .requestMatchers(HttpMethod.POST, "/reviews").authenticated()
                .requestMatchers(HttpMethod.GET, "/reviews/new").authenticated()
                .requestMatchers(HttpMethod.GET, "/reviews/edit/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/reviews/disable/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/reviews/delete/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/reviews/*").permitAll()

                .requestMatchers("/orders", "/orders/**").authenticated()

                // panel de usuarios para admins
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
        );

        http.formLogin(form ->
                form.loginPage("/login")
                .defaultSuccessUrl("/restaurants", true)
                .permitAll()
        );

        return http.build();
    }
}

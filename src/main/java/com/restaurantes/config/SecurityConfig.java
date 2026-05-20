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
                        // rutas publicas tanto GET como POST
                        .requestMatchers("/hola", "/adios", "/login",
                                "/register", "/css/**", "/images/**", "/webjars/**").permitAll()

                // de golpe:
//                .requestMatchers(HttpMethod.GET, "/restaurants", "/restaurants/*", "/dishes", "/dishes/*").permitAll()

                // separado de una en una
                .requestMatchers(HttpMethod.GET, "/restaurants").permitAll()
                .requestMatchers(HttpMethod.GET, "/restaurants/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/restaurants").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/deactivate/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/new").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/edit/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/dishes").permitAll()
                .requestMatchers(HttpMethod.GET, "/dishes/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/dishes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/dishes/new").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/dishes/edit/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                .requestMatchers(HttpMethod.GET, "/reviews/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/reviews").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/reviews/new").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/reviews/edit/*").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/reviews/disable/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/reviews/delete/*").hasRole("ADMIN")

                .requestMatchers("/orders", "/orders/**").authenticated()

                        // lo demás autenticado si o si
                    .anyRequest().authenticated()
//                        .anyRequest().permitAll()
        );

        http.formLogin(form ->
                form.loginPage("/login")
                .defaultSuccessUrl("/restaurants", true)
                .permitAll()
        );

        // TODO h2

        // TODO logout

        return http.build();
    }
}

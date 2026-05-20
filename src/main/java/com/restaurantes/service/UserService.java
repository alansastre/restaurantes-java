package com.restaurantes.service;

import com.restaurantes.dto.RegisterForm;
import com.restaurantes.model.User;
import com.restaurantes.model.enums.Role;
import com.restaurantes.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // metodo para buscar el usuario en base de datos por su username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // opcion tradicional:
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con username: " + username);
        }

        // opcion funcional:
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

    }


    public User register(RegisterForm form) {

        if (userRepository.existsByUsername(form.getUsername()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");

        if (userRepository.existsByEmail(form.getEmail()))
            throw new IllegalArgumentException("El correo electrónico ya existe");

        if (! form.getPassword().equals(form.getPasswordConfirm()))
            throw new IllegalArgumentException("Las contraseñas no coinciden");

//        if (! form.getAcceptTermsAndConditions())
//            throw new IllegalArgumentException("Obligatorio aceptar términos y condiciones de la plataforma.");

        User user = new User();
        user.setUsername(form.getUsername()); // el username se podría generar automáticamente en base al email
        user.setEmail(form.getEmail());
//         user.setPassword(form.getPassword()); // texto plano sin cifrar  admin
        user.setPassword(passwordEncoder.encode(form.getPassword())); // password cifrada con bcrypt $2a$10$vVJ3lkY2o85Hl8Cf95/xrOF/Dsv3kEtjJTlwFyyElAE6WRglzTao2
        user.setRole(Role.ROLE_USER); // por defecto no asignamos rol de admin a usuarios nuevos
        return userRepository.save(user); // guarda el usuario en base de datos
    }

    // TODO metodo para crear usuario admin
}

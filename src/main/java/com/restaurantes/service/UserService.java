package com.restaurantes.service;

import com.restaurantes.dto.RegisterForm;
import com.restaurantes.dto.UserStatsDTO;
import com.restaurantes.model.User;
import com.restaurantes.model.enums.Role;
import com.restaurantes.repository.OrderRepository;
import com.restaurantes.repository.ReviewRepository;
import com.restaurantes.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

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


    public List<User> findAll() {
        return userRepository.findAll();
        // no hace falta porque no mostramos la password en thymeleaf y no queremos pisar la password
//        return userRepository.findAll().stream().map(user -> {
//            user.setPassword(null); // ocultar la contraseña al devolver el usuario
//            return user;
//        }).toList();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public UserStatsDTO findStatsById(Long id) {
        return new UserStatsDTO(
                reviewRepository.countByUser_Id(id),
                reviewRepository.findByUser_Id(id),
                orderRepository.countByUser_Id(id),
                orderRepository.findByUser_IdOrderByDateDesc(id),
                orderRepository.calculateTotalMoneySpentByUserId(id)
        );
    }


    public User create(User user){
        if (userRepository.existsByUsername(user.getUsername()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");

        if (userRepository.existsByEmail(user.getEmail()))
            throw new IllegalArgumentException("El correo electrónico ya existe");

//        if (user.getPassword() == null || user.getPassword().isBlank())
//            throw new IllegalArgumentException("La contraseña es obligatoria");

        if (!StringUtils.hasText(user.getPassword()))
            throw new IllegalArgumentException("Password no puede estar vacía");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User userForm) {
        User userDB = findById(userForm.getId()); // primero sacamos el usuario de base de datos

        // Ver si username ocupado por otro usuario distinto a ese usuario userDB.getId()
        // si username ocupado por otro usuario entonces throw new IllegalArgumentException
        Optional<User> userOpt = userRepository.findByUsername(userForm.getUsername());
        if (userOpt.isPresent() && !userOpt.get().getId().equals(userForm.getId()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");

        // lo mismo para el email pero en estilo programación funcional
        userRepository.findByEmail(userForm.getEmail())
                .filter(user -> !user.getId().equals(userForm.getId()))
                .ifPresent(user -> {
                    throw new IllegalArgumentException("El email de usuario ya existe");
                });


        userDB.setUsername(userForm.getUsername());
        userDB.setEmail(userForm.getEmail());
        userDB.setRole(userForm.getRole());

        // si se ha introducido una nueva contraseña, la ciframos y actualizamos,
        // sino dejamos la contraseña actual sin cambios
        if(StringUtils.hasText(userForm.getPassword()))
            userDB.setPassword(passwordEncoder.encode(userForm.getPassword()));

        return userRepository.save(userDB); // guardamos el usuario actualizado en base de datos
    }

}

package com.restaurantes.service;

import com.restaurantes.model.User;
import com.restaurantes.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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


}

package com.restaurantes.controller;

import com.restaurantes.model.User;
import com.restaurantes.model.enums.Role;
import com.restaurantes.service.FileService;
import com.restaurantes.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@AllArgsConstructor
@Controller
public class UserController {

    private final FileService fileService;
    private final UserService userService;

    @GetMapping("admin/users")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/user-list";
    }

    // user detail  http://localhost:8080/admin/users/1
    @GetMapping("admin/users/{id}")
    public String detail(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id)); // User
        model.addAttribute("userStats", userService.findStatsById(id)); // UserStatsDTO
        return "users/user-detail";
    }

    /*
    Si queremos que user-form.html tenga passwordConfirm entonces es mejor usar aquí
    un DTO por ejemplo UserFormDTO con los campos de User más passwordConfirm,
    y en el metodo save() convertir ese DTO a User para guardarlo en la base de datos.
     */
    @GetMapping("admin/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("edit", false);
        return "users/user-form";
    }

    @GetMapping("admin/users/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        User user = userService.findById(id);
        user.setPassword(null); // no devolver esta password cifrada para evitar exponerla
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("edit", true);
        return "users/user-form";
    }

    // PostMapping admin/users
    @PostMapping("admin/users")
    public String save(
            @ModelAttribute User user,
            RedirectAttributes ra,
            @RequestParam("imageFile") MultipartFile imageFile
            ) {
        log.info("Guardando user {}", user.getUsername());

        String imageUrl = fileService.store(imageFile);
        if (imageUrl != null)
            user.setImageUrl(imageUrl);

        try {
            if (user.getId() == null) {
                user = userService.create(user);
                ra.addFlashAttribute("message", "usuario creado");
                log.info("Usuario creado {}", user);
            } else {
                user = userService.update(user);
                ra.addFlashAttribute("message", "usuario actualizado");
                log.info("Usuario actualizado {}", user);
            }
            return "redirect:/admin/users";
        } catch (Exception e) {
            log.warn("Error al guardar user {}", user, e);

            ra.addFlashAttribute("error", e.getMessage());
            return user.getId() == null ?
                    "redirect:/admin/users/new" : "redirect:/admin/users/edit/" + user.getId();
        }
    }



    @GetMapping("profile")
    public String profile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", userService.findById(user.getId())); // User
        model.addAttribute("userStats", userService.findStatsById(user.getId())); // UserStatsDTO
        return "users/user-detail";
    }

    @GetMapping("profile/edit")
    public String editProfile(Model model, @AuthenticationPrincipal User user) {
        User saved = userService.findById(user.getId());
        user.setPassword(null);
        model.addAttribute("user", saved);
        return "users/profile-form";
    }

    @PostMapping("profile")
    public String saveProfile(@ModelAttribute User userForm,
                               RedirectAttributes ra,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               @AuthenticationPrincipal User authenticatedUser,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        if (authenticatedUser == null || authenticatedUser.getId() == null) {
            log.error("Error usuario {} intentando editar otro usuario {}", authenticatedUser, userForm);
            ra.addFlashAttribute("error", "No tienes permisos");
            return "redirect:/profile";
        }
        // evitar que el usuario pueda cambiar su id, rol, active para evitar escalada de privilegios
        userForm.setId(authenticatedUser.getId());
        userForm.setRole(authenticatedUser.getRole());
        userForm.setActive(authenticatedUser.getActive());

        // imagen
        String imageUrl = fileService.store(imageFile);
        userForm.setImageUrl(imageUrl != null ? imageUrl : authenticatedUser.getImageUrl());

        User userUpdated = userService.update(userForm);

        // refrescar Spring Security para que el principal/navbar muestren los datos nuevos
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userUpdated,
                userUpdated.getPassword(),
                userUpdated.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(newAuth);

        SecurityContextHolder.setContext(context);

        new HttpSessionSecurityContextRepository().saveContext(context, request, response);

        ra.addFlashAttribute("message", "usuario actualizado");
        return  "redirect:/profile";
    }

}

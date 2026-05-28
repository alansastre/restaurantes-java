package com.restaurantes.controller;

import com.restaurantes.model.User;
import com.restaurantes.model.enums.Role;
import com.restaurantes.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@AllArgsConstructor
@Controller
public class UserController {

    private UserService userService;

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
        log.info("Imagen recibida {}", imageFile);


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



    // GetMapping profile

    // PostMapping profile

}

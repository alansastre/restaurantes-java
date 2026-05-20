package com.restaurantes.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterForm {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
}

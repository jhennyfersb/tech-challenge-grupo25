package com.br.arraydesabores.rede.presentation.dto.user;

import jakarta.validation.constraints.Email;

public class UserUpdateDTO {

    private String name;

    @Email
    private String email;

    private String login;

}

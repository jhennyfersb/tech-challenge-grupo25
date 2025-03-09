package com.br.arraydesabores.rede.presentation.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String name;

    @Email
    private String email;

    private String login;

}

package com.br.arraydesabores.rede.presentation.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserAuthDTO {
    Long id;
    String name;
    String login;
    String password;
    String email;
    Set<String> roles;

}

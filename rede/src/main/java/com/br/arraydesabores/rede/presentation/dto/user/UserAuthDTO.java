package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.presentation.dto.userRole.UserRoleDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UserAuthDTO {
    Long id;
    String name;
    String login;
    String password;
    String email;
    Set<UserRoleDTO> roles;
    Integer version;

}

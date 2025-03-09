package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.domain.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserCreateDTO {

    @NotNull(message = "Nome é obrigatório")
    private String name;

    @Email(message = "Email inválido")
    private String email;

    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;

    @NotNull(message = "Login é obrigatório")
    private String login;

    private List<Address> addresses = new ArrayList<>();

    private Set<String> roles = new HashSet<>();

}

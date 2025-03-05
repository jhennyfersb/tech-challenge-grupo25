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

    @NotNull
    private String name;

    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    private String login;

    private List<Address> addresses = new ArrayList<>();

    private Set<String> roles = new HashSet<>();

}

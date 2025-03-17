package com.br.arraydesabores.rede.presentation.dto;

import com.br.arraydesabores.rede.presentation.dto.address.AddressCreateDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotNull
    private String name;

    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    private String login;

    private AddressCreateDTO address;
}

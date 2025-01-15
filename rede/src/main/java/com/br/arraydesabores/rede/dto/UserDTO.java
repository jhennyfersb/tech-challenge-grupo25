package com.br.arraydesabores.rede.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(
        String name,
        @NotNull
        String login,
        @Size(min = 6, max = 20)
        String password,
        @Email
        String email,
        AddressDTO addressDTO
) {
}

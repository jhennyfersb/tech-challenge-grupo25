package com.br.arraydesabores.rede.presentation.dto;

import com.br.arraydesabores.rede.domain.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(@NotNull String name,
                                 @Email String email,
                                 @Size(min = 6 , max = 20) String password,
                                 @NotNull
                                 String login,
                                 @NotNull
                                 Address address) {
}

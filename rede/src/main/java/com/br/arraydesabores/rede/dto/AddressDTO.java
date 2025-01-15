package com.br.arraydesabores.rede.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
        @NotNull
        String street,
        @NotNull
        Integer number,
        @NotNull
        String city) {
}

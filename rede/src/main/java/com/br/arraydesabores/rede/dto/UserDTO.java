package com.br.arraydesabores.rede.dto;

public record UserDTO(
        String name,
        String login,
        String password,
        String email,
        AddressDTO addressDTO
) {
}

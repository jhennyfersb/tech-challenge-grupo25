package com.br.arraydesabores.rede.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN("Administrador"),
    USER("Usuário");


    private final String description;

    public String getDescription() {
        return description;
    }

}

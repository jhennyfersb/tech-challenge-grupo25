package com.br.arraydesabores.rede.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleType {
    OWNER("Dono do Restaurante"),
    ADMIN("Administrador"),
    USER("Usuário"),
    CONSUMER("Consumidor");

    private final String description;

}

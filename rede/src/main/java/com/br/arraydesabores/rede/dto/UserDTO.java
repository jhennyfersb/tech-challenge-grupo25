package com.br.arraydesabores.rede.dto;

import com.br.arraydesabores.rede.model.User;
import org.springframework.util.ObjectUtils;

import java.util.List;

public record UserDTO(
        String name,
        String login,
        String password,
        String email,
        AddressDTO addressDTO
) {

    public User toModel() {
        var user = new User();
        user.setName(this.name());
        user.setLogin(this.login());
        user.setPassword(this.password());
        user.setEmail(this.email());
        if (!ObjectUtils.isEmpty(this.addressDTO())) {
            user.setAddresses(List.of(this.addressDTO().toModel()));
        }
        return user;
    }
}

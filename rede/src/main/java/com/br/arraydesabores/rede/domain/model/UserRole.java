package com.br.arraydesabores.rede.domain.model;

import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class UserRole extends DomainMain {

    private UserRoleType role;
    private User user;

    public UserRole(Long id, UserRoleType role) {
        super(id);
        this.role = role;
    }
}

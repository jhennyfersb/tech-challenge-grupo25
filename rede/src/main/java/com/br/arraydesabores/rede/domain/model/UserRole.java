package com.br.arraydesabores.rede.domain.model;

import com.br.arraydesabores.rede.domain.enums.UserRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class UserRole extends DomainMain {

    private UserRoleEnum role;

    public UserRole(Long id) {
        super();
        this.setId(id);
    }

}

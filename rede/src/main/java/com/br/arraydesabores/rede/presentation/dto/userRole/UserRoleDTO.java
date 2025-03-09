package com.br.arraydesabores.rede.presentation.dto.userRole;

import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.DomainMain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserRoleDTO extends DomainMain {
    private UserRoleType role;
}

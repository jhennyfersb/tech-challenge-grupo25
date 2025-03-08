package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.domain.model.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UserRoleAssignRequest {
    private Long userId;
    private Set<UserRole> roles;
}

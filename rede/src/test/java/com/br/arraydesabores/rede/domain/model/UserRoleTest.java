package com.br.arraydesabores.rede.domain.model;

import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    @DisplayName("Deve criar um UserRole com construtor com id e role")
    void createUserRoleWithIdAndRole() {
        UserRole userRole = new UserRole(1L, UserRoleType.ADMIN);
        assertEquals(1L, userRole.getId());
        assertEquals(UserRoleType.ADMIN, userRole.getRole());
    }

}
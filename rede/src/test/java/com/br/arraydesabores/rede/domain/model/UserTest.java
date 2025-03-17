package com.br.arraydesabores.rede.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    @DisplayName("Testa o construtor customizado com id")
    public void testCustomConstructorWithId() {
        User user = new User(1L);
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

}
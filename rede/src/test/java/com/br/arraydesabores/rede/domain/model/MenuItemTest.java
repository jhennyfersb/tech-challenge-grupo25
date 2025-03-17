package com.br.arraydesabores.rede.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um item de menu com nome vazio")
    void shouldThrowExceptionWhenCreateMenuItemWithNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            MenuItem menuItem = new MenuItem();
            menuItem.setName("");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um item com preço negativo")
    void shouldThrowExceptionWhenCreateMenuItemWithNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            MenuItem menuItem = new MenuItem();
            menuItem.setPrice(null);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um item com preço zero")
    void shouldThrowExceptionWhenCreateMenuItemWithZeroPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            MenuItem menuItem = new MenuItem();
            menuItem.setPrice(null);
        });
    }

    @Test
    @DisplayName("Deve criar um item de menu com nome e preço válidos")
    void shouldCreateMenuItemWithNameAndPriceValid() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Café");
        menuItem.setPrice(BigDecimal.valueOf(5.0));
        assertEquals("Café", menuItem.getName());
        assertEquals(BigDecimal.valueOf(5.0), menuItem.getPrice());
    }

}
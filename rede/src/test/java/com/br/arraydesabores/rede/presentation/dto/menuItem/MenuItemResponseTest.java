package com.br.arraydesabores.rede.presentation.dto.menuItem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemResponseTest {

    @Test
    @DisplayName("Testa o método setPrice")
    void testSetPrice() {
        MenuItemResponse menuItemResponse = new MenuItemResponse();
        menuItemResponse.setPrice(BigDecimal.valueOf(10.0));
        assertEquals("R$\u00A010,00", menuItemResponse.getPriceFormatted());
    }

}
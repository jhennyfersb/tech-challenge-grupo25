package com.br.arraydesabores.rede.presentation.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberUtilsTest {

    @Test
    @DisplayName("Testa formatação de moeda")
    void testFormatCurrency() {
        String formattedCurrency = NumberUtils.formatCurrency(BigDecimal.valueOf(100.0));
        assertEquals("R$\u00A0100,00", formattedCurrency);
    }

}
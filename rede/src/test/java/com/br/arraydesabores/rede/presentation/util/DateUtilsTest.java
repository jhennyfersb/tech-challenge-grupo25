package com.br.arraydesabores.rede.presentation.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    @DisplayName("Testa formatação de LocalDate")
    void testFormatLocalDate() {
        LocalDate date = LocalDate.of(2023, 10, 5);

        String formattedDate = DateUtils.formatLocalDate(date);

        assertEquals("05/10/2023", formattedDate);
    }

    @Test
    void testFormatLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 5, 14, 30);

        String formattedDateTime = DateUtils.formatLocalDateTime(dateTime);

        assertEquals("05/10/2023 14:30", formattedDateTime);
    }

    @Test
    void testFormatLocalTime() {
        LocalTime time = LocalTime.of(14, 30);

        String formattedTime = DateUtils.formatLocalTime(time);

        // Assert
        assertEquals("14:30", formattedTime);
    }

}
package com.br.arraydesabores.rede.presentation.dto;

import com.br.arraydesabores.rede.presentation.dto.user.UserResponse;
import com.br.arraydesabores.rede.presentation.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

class UserResponseDTOTest {

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
    }


    @Test
    @DisplayName("Deve formatar a data de criação")
    void testSetCreatedAtFormatted() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 3, 18, 12, 30, 45);
        String expectedFormattedDate = "18/03/2025 12:30";
        userResponse.setCreatedAt(createdAt);

        try (MockedStatic<DateUtils> mockedDateUtils = Mockito.mockStatic(DateUtils.class)) {
            mockedDateUtils.when(() -> DateUtils.formatLocalDateTime(createdAt)).thenReturn(expectedFormattedDate);

            userResponse.setCreatedAtFormatted(expectedFormattedDate);


            assertEquals(expectedFormattedDate, userResponse.getCreatedAtFormatted());
            mockedDateUtils.verify(() -> DateUtils.formatLocalDateTime(createdAt), times(1));

        }
    }

    @Test
    @DisplayName("Deve formatar a data de atualização")
    void testSetUpdatedAtFormatted() {
        LocalDateTime updatedAt = LocalDateTime.of(2025, 3, 18, 12, 30, 45);
        String expectedFormattedDate = "18/03/2025 12:30";
        userResponse.setUpdatedAt(updatedAt);

        try (MockedStatic<DateUtils> mockedDateUtils = Mockito.mockStatic(DateUtils.class)) {
            mockedDateUtils.when(() -> DateUtils.formatLocalDateTime(updatedAt)).thenReturn(expectedFormattedDate);

            userResponse.setUpdatedAtFormatted(null); // O valor passado aqui não importa

            assertEquals(expectedFormattedDate, userResponse.getUpdatedAtFormatted());
            mockedDateUtils.verify(() -> DateUtils.formatLocalDateTime(updatedAt), times(1));
        }
    }

}
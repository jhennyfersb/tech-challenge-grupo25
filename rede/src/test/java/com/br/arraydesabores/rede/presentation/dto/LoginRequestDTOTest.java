package com.br.arraydesabores.rede.presentation.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Testa email e senha válidos")
    void testValidEmailAndPassword() {
        String email = "valid.email@example.com";
        String password = "password123";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(loginRequestDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Testa email inválido")
    void testInvalidEmail() {
        String invalidEmail = "invalid-email";
        String password = "password123";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(invalidEmail, password);
        Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(loginRequestDTO);

        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("deve ser um endereço de e-mail bem formado"));
    }

    @Test
    @DisplayName("Testa senha inválida")
    void testInvalidPasswordSize() {
        String email = "test@example.com";
        String shortPassword = "pass";
        String longPassword = "thisisaverylongpassword";

        LoginRequestDTO shortPasswordDTO = new LoginRequestDTO(email, shortPassword);
        LoginRequestDTO longPasswordDTO = new LoginRequestDTO(email, longPassword);

        Set<ConstraintViolation<LoginRequestDTO>> shortPasswordViolations = validator.validate(shortPasswordDTO);
        Set<ConstraintViolation<LoginRequestDTO>> longPasswordViolations = validator.validate(longPasswordDTO);

        assertEquals(1, shortPasswordViolations.size());
        assertEquals(1, longPasswordViolations.size());

        assertTrue(shortPasswordViolations.iterator().next().getMessage().contains("tamanho deve ser entre 6 e 20"));
    }

}
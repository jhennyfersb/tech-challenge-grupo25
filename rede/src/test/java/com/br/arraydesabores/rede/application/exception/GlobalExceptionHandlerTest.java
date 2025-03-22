package com.br.arraydesabores.rede.application.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve tratar exceção de endereço não encontrado")
    void handleAddressNotFoundException() {
        String errorMessage = "Address not found";
        AddressNotFoundException exception = new AddressNotFoundException(errorMessage);

        ResponseEntity<String> response = exceptionHandler.handleAddressNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção de usuário não encontrado")
    void handleUserNotFoundException() {
        String errorMessage = "User not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        ResponseEntity<String> response = exceptionHandler.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção de restaurante não encontrado")
    void handleRestaurantNotFoundException() {
        RestaurantNotFoundException exception = new RestaurantNotFoundException();

        ResponseEntity<String> response = exceptionHandler.handleRestaurantNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurante não encontrado", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção de item do menu não encontrado")
    void handleMenuItemNotFoundException() {
        MenuItemNotFoundException exception = new MenuItemNotFoundException();

        ResponseEntity<String> response = exceptionHandler.handleMenuItemNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Item do Menu não encontrado", response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção de argumentos inválidos")
    void handleMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        when(exception.getBindingResult()).thenReturn(bindingResult);
        
        FieldError fieldError1 = new FieldError("objectName", "field1", "Error message 1");
        FieldError fieldError2 = new FieldError("objectName", "field2", "Error message 2");
        
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Error message 1", response.getBody().get("field1"));
        assertEquals("Error message 2", response.getBody().get("field2"));
    }

    @Test
    @DisplayName("Deve tratar exceção de argumento ilegal")
    void handleIllegalArgumentException() {
        String errorMessage = "Invalid argument";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        ResponseEntity<String> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção genérica")
    void handleGenericException() {
        String errorMessage = "Generic error";
        Exception exception = new Exception(errorMessage);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Exception", response.getBody().get("error"));
        assertEquals(errorMessage, response.getBody().get("message"));
    }

    @Test
    @DisplayName("Deve tratar exceção de tempo de execução")
    void handleRuntimeException() {
        String errorMessage = "Runtime error";
        RuntimeException exception = new RuntimeException(errorMessage);

        ResponseEntity<String> response = exceptionHandler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocorreu um erro interno: " + errorMessage, response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção de acesso proibido")
    void handleForbiddenException() {
        String errorMessage = "Access forbidden";
        ForbiddenException exception = new ForbiddenException(errorMessage);

        ResponseEntity<String> response = exceptionHandler.handleForbiddenException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    @DisplayName("Deve tratar exceção de acesso negado")
    void handleAccessDeniedException() {
        AccessDeniedException exception = new AccessDeniedException("This message will not be used");

        ResponseEntity<String> response = exceptionHandler.handleAccessDenied();

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Acesso negado: você não tem permissão para esta operação.", response.getBody());
    }
} 
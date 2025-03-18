package com.br.arraydesabores.rede.application.usecases.auth;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.service.TokenService;
import com.br.arraydesabores.rede.presentation.dto.LoginRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Test
    @DisplayName("Deve autenticar o usuário com sucesso")
    void shouldAuthenticateUserSuccessfully() throws Exception {
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword";
        String token = "generatedToken";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        User user = new User();
        user.setId(1L);
        user.setPassword(encodedPassword);

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(userGateway.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn(token);

        authenticateUserUseCase.execute(loginRequestDTO, response);

        verify(userGateway, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(tokenService, times(1)).generateToken(user);

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(response, times(1)).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();
        assertNotNull(cookie);
        assertEquals("auth_token", cookie.getName());
        assertEquals(token, cookie.getValue());
        assertTrue(cookie.isHttpOnly());
        assertTrue(cookie.getSecure());
        assertEquals("/", cookie.getPath());
        assertEquals(60 * 60 * 24 * 7, cookie.getMaxAge());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não for encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        String email = "nonexistent@example.com";
        String password = "password123";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(userGateway.findByEmail(email)).thenThrow(new UserNotFoundException("User not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticateUserUseCase.execute(loginRequestDTO, response);
        });

        assertEquals("Dados inválidos", exception.getMessage());
        verify(userGateway, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(tokenService, never()).generateToken(any());
        verify(response, never()).addCookie(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha estiver incorreta")
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        String email = "test@example.com";
        String password = "wrongPassword";
        String encodedPassword = "encodedPassword";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);

        User user = new User();
        user.setId(1L);
        user.setPassword(encodedPassword);

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(userGateway.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticateUserUseCase.execute(loginRequestDTO, response);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(userGateway, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(tokenService, never()).generateToken(any());
        verify(response, never()).addCookie(any());
    }
}
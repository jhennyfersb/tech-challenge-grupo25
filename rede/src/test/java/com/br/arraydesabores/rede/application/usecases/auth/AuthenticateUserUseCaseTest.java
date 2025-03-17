package com.br.arraydesabores.rede.application.usecases.auth;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.service.TokenService;
import com.br.arraydesabores.rede.presentation.dto.LoginRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    @Description("Deve autenticar um usuário com sucesso")
    void shouldAuthenticateUserSuccessfully() {
//        var user = new User(1L);
//        HttpServletResponse response = null;
//        var cookie = new Cookie("auth_token", "token");
//
//        when(userGateway.findByEmail("email@email.com")).thenReturn(user);
//        when(tokenService.generateToken(user)).thenReturn("token");
//        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
//
//         authenticateUserUseCase.execute(new LoginRequestDTO("email", "password"), response);
//
//         assertEquals("token", cookie.getValue());
    }

}
package com.br.arraydesabores.rede.application.usecases.auth;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.service.TokenService;
import com.br.arraydesabores.rede.presentation.dto.LoginRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticateUserUseCase {

    private final IUserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public void execute(LoginRequestDTO body, HttpServletResponse response) {
        User user = getUserByEmail(body.email());

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);

        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(cookie);
    }

    private User getUserByEmail(String email) {
        try {
            return userGateway.findByEmail(email);
        } catch (UserNotFoundException e) {
            throw new IllegalArgumentException("Dados inválidos");
        }
    }

}

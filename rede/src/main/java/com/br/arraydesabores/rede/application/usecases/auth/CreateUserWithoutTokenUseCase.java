package com.br.arraydesabores.rede.application.usecases.auth;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.application.usecases.user.CreateUserUseCase;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.security.TokenService;
import com.br.arraydesabores.rede.presentation.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUserWithoutTokenUseCase {

    private final IUserGateway userGateway;
    private final ModelMapper modelMapper;
    private final CreateUserUseCase createUserUseCase;
    private final TokenService tokenService;

    public User execute(RegisterRequestDTO request, HttpServletResponse response) {
        if (userGateway.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        var userCreate = modelMapper.map(request, UserCreateDTO.class);
        var userSave = createUserUseCase.execute(userCreate);
        String token = tokenService.generateToken(modelMapper.map(userSave, User.class));

        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(cookie);

        return userSave;
    }
}

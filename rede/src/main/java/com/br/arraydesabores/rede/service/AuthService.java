package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.application.usecases.user.CreateUserUseCase;
import com.br.arraydesabores.rede.infrastructure.gateways.UserGatewayImpl;
import com.br.arraydesabores.rede.presentation.dto.LoginRequestDTO;
import com.br.arraydesabores.rede.presentation.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.presentation.dto.ResponseDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserDTO;
import com.br.arraydesabores.rede.infrastructure.security.TokenService;
import com.br.arraydesabores.rede.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;
    private final CreateUserUseCase createUserUseCase;
    private final UserGatewayImpl userGateway;


    public String registerUser(RegisterRequestDTO body) throws IllegalArgumentException {
        if (userGateway.existsByEmail(body.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        var userCreate = modelMapper.map(body, UserCreateDTO.class);
        var userSave = createUserUseCase.execute(userCreate);
        return tokenService.generateToken(modelMapper.map(userSave, User.class));
    }


    public ResponseDTO authenticateUser(LoginRequestDTO body) {
        User user = userGateway.findByEmail(body.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = tokenService.generateToken(modelMapper.map(user, User.class));
        return new ResponseDTO(user.getName(), token);
    }


}

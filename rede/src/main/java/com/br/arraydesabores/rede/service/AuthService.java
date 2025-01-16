package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.dto.LoginRequestDTO;
import com.br.arraydesabores.rede.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.dto.RegisterResponseDTO;
import com.br.arraydesabores.rede.dto.UserDTO;
import com.br.arraydesabores.rede.infra.security.TokenService;
import com.br.arraydesabores.rede.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    public RegisterResponseDTO login(LoginRequestDTO body) {
        var user = userService.findByEmail(body.email());
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new RegisterResponseDTO(user.getName(), token);
        }
        return null;
    }

    public RegisterResponseDTO register(RegisterRequestDTO body) {
        if (!userService.existsByEmail(body.email())) {
            var user = userService.create(
                    new UserDTO(body.name(), null, body.password(), body.email(), null)
            );
            return new RegisterResponseDTO(user.getName(), tokenService.generateToken(user));
        }
        return null;
    }

}
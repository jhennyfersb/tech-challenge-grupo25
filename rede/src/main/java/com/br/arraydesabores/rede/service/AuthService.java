package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.dto.LoginRequestDTO;
import com.br.arraydesabores.rede.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.dto.ResponseDTO;
import com.br.arraydesabores.rede.infra.security.TokenService;
import com.br.arraydesabores.rede.model.User;
import com.br.arraydesabores.rede.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }


    public String registerUser(RegisterRequestDTO body) throws IllegalArgumentException {
        repository.findByEmail(body.email()).ifPresent(user -> {
            throw new IllegalArgumentException("Email already exists");
        });

        User newUser = User.builder()
                .email(body.email())
                .password(passwordEncoder.encode(body.password()))
                .name(body.name())
                .roles(new HashSet<>(Collections.singleton("ROLE_USER")))
                .build();

        User userSave = repository.save(newUser);
        return tokenService.generateToken(userSave);
    }


    public ResponseDTO authenticateUser(LoginRequestDTO body) {
        User user = repository.findByEmail(body.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);
        return new ResponseDTO(user.getName(), token);
    }


}

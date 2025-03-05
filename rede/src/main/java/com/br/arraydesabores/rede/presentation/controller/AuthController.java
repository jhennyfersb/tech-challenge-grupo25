package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.usecases.auth.AuthenticateUserUseCase;
import com.br.arraydesabores.rede.application.usecases.auth.CreateUserWithoutTokenUseCase;
import com.br.arraydesabores.rede.presentation.dto.LoginRequestDTO;
import com.br.arraydesabores.rede.presentation.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.presentation.dto.ResponseDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CreateUserWithoutTokenUseCase createUserWithoutTokenUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO body, HttpServletResponse response) {
        authenticateUserUseCase.execute(body, response);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body,
                                                    HttpServletResponse response) {
        var user = createUserWithoutTokenUseCase.execute(body, response);
        return ResponseEntity.ok().body(modelMapper.map(user, UserResponseDTO.class));
    }

}

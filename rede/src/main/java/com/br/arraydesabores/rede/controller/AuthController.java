package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.dto.LoginRequestDTO;
import com.br.arraydesabores.rede.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.dto.ResponseDTO;
import com.br.arraydesabores.rede.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO body) {
        try {
            ResponseDTO response = authService.authenticateUser(body);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO body) {
        try {
            String token = authService.registerUser(body);
            return ResponseEntity.ok(new ResponseDTO(body.name(), token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

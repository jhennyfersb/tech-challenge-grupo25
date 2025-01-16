package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.dto.LoginRequestDTO;
import com.br.arraydesabores.rede.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.dto.RegisterResponseDTO;
import com.br.arraydesabores.rede.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RegisterResponseDTO> login(@RequestBody LoginRequestDTO body) {
        var response = this.authService.login(body);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        var newUser = this.authService.register(body);
        if (newUser != null) {
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.badRequest().build();
    }

}

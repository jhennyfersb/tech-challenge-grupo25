package com.br.arraydesabores.rede.presentation.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserListDTO {
    private Long Id;
    private String name;
    private String login;
    private String email;
    private LocalDateTime createdAt;
}

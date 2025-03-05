package com.br.arraydesabores.rede.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(@Email String email, @Size(min = 6,  max = 20) String password) {
}

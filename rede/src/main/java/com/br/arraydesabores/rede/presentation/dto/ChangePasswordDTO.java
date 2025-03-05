package com.br.arraydesabores.rede.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(
        @NotBlank(message = "Senha antiga é obrigatória")
        String oldPassword,

        @NotBlank(message = "Nova senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String newPassword
) {
}

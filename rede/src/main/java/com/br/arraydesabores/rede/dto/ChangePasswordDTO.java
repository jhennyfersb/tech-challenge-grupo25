package com.br.arraydesabores.rede.dto;

import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(@Size(min = 6 , max = 20) String oldPassword, @Size(min = 6 ,  max = 20) String newPassword) {
}

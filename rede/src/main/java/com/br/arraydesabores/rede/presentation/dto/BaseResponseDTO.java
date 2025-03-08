package com.br.arraydesabores.rede.presentation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

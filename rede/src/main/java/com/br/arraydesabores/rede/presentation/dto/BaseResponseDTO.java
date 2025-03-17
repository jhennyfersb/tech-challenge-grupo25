package com.br.arraydesabores.rede.presentation.dto;

import com.br.arraydesabores.rede.presentation.util.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String createdAtFormatted;
    private LocalDateTime updatedAt;
    private String updatedAtFormatted;

    public void setCreatedAtFormatted(String createdAtFormatted) {
        this.createdAtFormatted = DateUtils.formatLocalDateTime(this.createdAt);
    }

    public void setUpdatedAtFormatted(String updatedAtFormatted) {
        this.updatedAtFormatted = DateUtils.formatLocalDateTime(this.updatedAt);
    }
}

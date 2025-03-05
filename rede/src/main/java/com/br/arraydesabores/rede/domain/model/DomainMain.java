package com.br.arraydesabores.rede.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class DomainMain implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer version;
}

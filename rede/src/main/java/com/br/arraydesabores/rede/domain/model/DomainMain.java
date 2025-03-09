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

    public DomainMain(Long id) {
        this.createdAt = LocalDateTime.now();
        this.id = id;
    }

    public DomainMain() {
        this.createdAt = LocalDateTime.now();
    }
}

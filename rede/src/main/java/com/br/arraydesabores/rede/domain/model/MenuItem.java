package com.br.arraydesabores.rede.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItem extends DomainMain {

    private String name;
    private String description;
    private BigDecimal price;
    private boolean availableOnlyInRestaurant;
    private String photoPath;

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do item não pode ser vazio");
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do item não pode ser vazio, negativo ou zero");
        }
        this.price = price;
    }

}

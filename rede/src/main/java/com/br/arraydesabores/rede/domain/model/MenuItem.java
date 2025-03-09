package com.br.arraydesabores.rede.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuItem extends DomainMain {

    private String name;
    private String description;
    private Double price;
    private Boolean availableOnlyInRestaurant;
    private String photoPath;
    private Restaurant restaurant;
}

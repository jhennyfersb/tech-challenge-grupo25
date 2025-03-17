package com.br.arraydesabores.rede.domain.model;

import com.br.arraydesabores.rede.domain.enums.CuisineType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Restaurant extends DomainMain {

    private String name;
    private Address address;
    private CuisineType cuisineType;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private User owner;
    private List<MenuItem> menuItems = new ArrayList<>();
}

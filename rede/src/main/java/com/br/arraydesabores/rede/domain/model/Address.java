package com.br.arraydesabores.rede.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends DomainMain {

    private String street;

    private Integer number;

    private String zipCode;

    private String city;

    private String state;

    private String complement;

    private String country;

    private User user;
}

package com.br.arraydesabores.rede.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Address extends DomainMain {

    private String street;
    private Integer number;
    private String city;

}

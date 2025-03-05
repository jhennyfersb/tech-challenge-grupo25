package com.br.arraydesabores.rede.domain.model;

import com.br.arraydesabores.rede.presentation.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends DomainMain {

    private String street;

    private Integer number;

    private String city;

    private String state;

    private String complement;

    private String zipCode;

    private String country;


    public AddressDTO toDTO() {
        return AddressDTO.builder()
                .street(this.street)
                .number(this.number)
                .city(this.city)
                .state(this.state)
                .complement(this.complement)
                .zipcode(this.zipCode)
                .country(this.country)
                .build();
    }

}

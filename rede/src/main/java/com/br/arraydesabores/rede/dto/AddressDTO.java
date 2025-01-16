package com.br.arraydesabores.rede.dto;

import com.br.arraydesabores.rede.model.Address;

public record AddressDTO(
        String street,
        Integer number,
        String city
) {

    public Address toModel() {
        var address = new Address();
        address.setStreet(this.street());
        address.setNumber(this.number());
        address.setCity(this.city());
        return address;
    }
}

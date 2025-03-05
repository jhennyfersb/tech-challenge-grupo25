package com.br.arraydesabores.rede.presentation.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressCreateDTO {
    @NotNull
    String street;
    @NotNull
    Integer number;
    @NotNull
    String city;

    @Size(min = 8, max = 8)
    String zipcode;

    String complement;
    String state;
    String country;
}

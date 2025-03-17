package com.br.arraydesabores.rede.presentation.dto.address;

import jakarta.validation.constraints.NotNull;
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

}

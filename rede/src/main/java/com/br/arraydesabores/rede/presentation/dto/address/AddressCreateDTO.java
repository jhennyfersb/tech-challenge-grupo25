package com.br.arraydesabores.rede.presentation.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressCreateDTO {
    @NotNull
    String street;
    @NotNull
    Integer number;
    @NotNull
    String city;

}

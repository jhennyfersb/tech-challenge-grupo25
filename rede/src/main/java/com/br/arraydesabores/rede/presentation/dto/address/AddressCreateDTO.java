package com.br.arraydesabores.rede.presentation.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateDTO {
    @NotNull
    String street;
    @NotNull
    Integer number;
    @NotNull
    String city;

}

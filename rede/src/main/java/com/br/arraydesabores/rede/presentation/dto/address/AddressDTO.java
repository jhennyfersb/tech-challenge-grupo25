package com.br.arraydesabores.rede.presentation.dto.address;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {
    Long id;
    @NotNull
    String street;
    @NotNull
    Integer number;
    @NotNull
    String city;
    String complement;
    String state;
    String country;
    String zipcode;
    Long userId;
}

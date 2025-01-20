package com.br.arraydesabores.rede.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

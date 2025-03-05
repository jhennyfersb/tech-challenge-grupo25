package com.br.arraydesabores.rede.presentation.dto.address;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressResponseDTO {
    Long id;
    String street;
    Integer number;
    String city;
    String zipcode;
    String complement;
    String state;
    String country;
    LocalDateTime createdAt;
}

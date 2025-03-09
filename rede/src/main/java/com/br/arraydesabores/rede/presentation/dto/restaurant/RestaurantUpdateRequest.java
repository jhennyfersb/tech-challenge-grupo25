package com.br.arraydesabores.rede.presentation.dto.restaurant;

import com.br.arraydesabores.rede.domain.enums.CuisineType;
import com.br.arraydesabores.rede.presentation.dto.address.AddressCreateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantUpdateRequest {

    @NotNull
    private String name;
    @NotNull
    private AddressCreateDTO address;
    @NotNull
    private CuisineType cuisineType;
    @NotNull
    private LocalTime openingTime;
    @NotNull
    private LocalTime closingTime;
}

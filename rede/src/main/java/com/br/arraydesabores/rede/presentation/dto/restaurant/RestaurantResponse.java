package com.br.arraydesabores.rede.presentation.dto.restaurant;

import com.br.arraydesabores.rede.presentation.dto.BaseResponseDTO;
import com.br.arraydesabores.rede.presentation.dto.address.AddressDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RestaurantResponse extends BaseResponseDTO {

    private String name;
    private AddressDTO address;
    private String cuisineType;
    private String openingAt;
    private String closingAt;

}

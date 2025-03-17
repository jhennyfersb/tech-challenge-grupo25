package com.br.arraydesabores.rede.application.criteria;

import com.br.arraydesabores.rede.domain.enums.CuisineType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantCriteria extends PageableCriteria {
    private Long ownerId;
    private CuisineType cuisineType;
}

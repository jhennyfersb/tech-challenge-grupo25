package com.br.arraydesabores.rede.application.criteria;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemCriteria extends PageableCriteria {
    private Long restaurantId;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private String name;

}

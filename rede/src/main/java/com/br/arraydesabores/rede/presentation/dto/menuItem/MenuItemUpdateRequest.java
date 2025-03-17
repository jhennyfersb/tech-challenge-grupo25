package com.br.arraydesabores.rede.presentation.dto.menuItem;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class MenuItemUpdateRequest {

    private String name;
    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    private boolean availableOnlyInRestaurant;

    @URL
    private String photoPath;

}

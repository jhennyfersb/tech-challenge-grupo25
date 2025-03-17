package com.br.arraydesabores.rede.presentation.dto.menuItem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class MenuItemCreateRequest {

    @NotNull
    private String name;
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotNull
    private boolean availableOnlyInRestaurant;

    @URL
    private String photoPath;

}

package com.br.arraydesabores.rede.presentation.dto.menuItem;

import com.br.arraydesabores.rede.presentation.dto.BaseResponseDTO;
import com.br.arraydesabores.rede.presentation.util.NumberUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MenuItemResponse extends BaseResponseDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private String priceFormatted;
    private boolean availableOnlyInRestaurant;
    private String photoPath;

    public void setPriceFormatted(String priceFormatted) {
        this.priceFormatted = NumberUtils.formatCurrency(this.price);
    }
}

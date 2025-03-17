package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;
    private final ModelMapper modelMapper;

    public MenuItem execute(Long restaurantId, Long id, MenuItemUpdateRequest input) {
        var restaurant = restaurantGateway.findById(restaurantId);
        RestaurantOwnershipValidator.IsOwned(restaurant);

        var item = menuItemGateway.findById(id);
        modelMapper.map(input, item);
        return menuItemGateway.save(restaurant, item);
    }

}

package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemCreateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;
    private final ModelMapper modelMapper;

    public MenuItem execute(Long restaurantId, MenuItemCreateRequest input) {
        var restaurant = restaurantGateway.findById(restaurantId);
        RestaurantOwnershipValidator.IsOwned(restaurant);

        var item = modelMapper.map(input, MenuItem.class);
        return menuItemGateway.save(restaurant, item);
    }

}

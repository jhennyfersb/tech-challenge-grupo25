package com.br.arraydesabores.rede.application.usecases.menuItem;

import org.springframework.stereotype.Service;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;

    public void execute(Long restaurantId, Long id) {
        var restaurant = restaurantGateway.findById(restaurantId);
        RestaurantOwnershipValidator.IsOwned(restaurant);

        var item = menuItemGateway.findById(id);
        menuItemGateway.deleteById(item.getId());
    }

}

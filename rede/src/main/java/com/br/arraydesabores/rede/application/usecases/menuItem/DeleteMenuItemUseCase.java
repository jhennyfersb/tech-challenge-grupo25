package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuItemUseCase {
    private final IMenuItemGateway menuItemGateway;
    private final IRestaurantGateway restaurantGateway;
    private final ModelMapper modelMapper;

    public void execute(Long restaurantId, Long id) {
        var restaurant = restaurantGateway.findById(restaurantId);
        RestaurantOwnershipValidator.IsOwned(restaurant);

        var item = menuItemGateway.findById(id);
        menuItemGateway.deleteById(item.getId());
    }

}

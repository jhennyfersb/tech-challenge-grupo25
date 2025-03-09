package com.br.arraydesabores.rede.application.usecases.restaurant;


import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRestaurantUseCase {

    private final IRestaurantGateway restaurantGateway;
    private final ModelMapper modelMapper;

    public void execute(Long id) {
        var restaurant = restaurantGateway.findById(id);
        RestaurantOwnershipValidator.IsOwned(restaurant);
        restaurantGateway.delete(restaurant);
    }
}

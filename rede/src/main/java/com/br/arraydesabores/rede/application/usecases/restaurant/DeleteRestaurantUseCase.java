package com.br.arraydesabores.rede.application.usecases.restaurant;

import org.springframework.stereotype.Service;

import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRestaurantUseCase {

    private final IRestaurantGateway restaurantGateway;

    public void execute(Long id) {
        var restaurant = restaurantGateway.findById(id);
        RestaurantOwnershipValidator.IsOwned(restaurant);
        restaurantGateway.delete(restaurant);
    }
}

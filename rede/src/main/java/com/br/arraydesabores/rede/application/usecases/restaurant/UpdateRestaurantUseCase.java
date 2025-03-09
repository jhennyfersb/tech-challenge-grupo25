package com.br.arraydesabores.rede.application.usecases.restaurant;


import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantValidator;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateRestaurantUseCase {

    private final IRestaurantGateway restaurantGateway;
    private final ModelMapper modelMapper;

    public Restaurant execute(Long id, RestaurantUpdateRequest input) {
        var restaurant = restaurantGateway.findById(id);
        RestaurantValidator.IsOwned(restaurant);

        modelMapper.map(input, restaurant);

        return restaurantGateway.save(restaurant);
    }
}

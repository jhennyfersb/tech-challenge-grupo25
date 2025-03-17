package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdRestaurantsUseCase {

    private final IRestaurantGateway restaurantGateway;

    public Restaurant execute(Long id) {
        return restaurantGateway.findById(id);
    }
}

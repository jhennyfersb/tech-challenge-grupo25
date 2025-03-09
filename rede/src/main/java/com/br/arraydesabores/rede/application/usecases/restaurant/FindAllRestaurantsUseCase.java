package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllRestaurantsUseCase {

    private final IRestaurantGateway restaurantGateway;

    public Page<Restaurant> execute(RestaurantCriteria criteria) {
        return restaurantGateway.findAll(criteria);
    }
}

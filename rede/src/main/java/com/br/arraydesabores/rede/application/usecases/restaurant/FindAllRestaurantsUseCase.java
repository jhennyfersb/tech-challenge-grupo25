package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllRestaurantsUseCase {

    private final IRestaurantGateway restaurantGateway;

    public Page<Restaurant> execute(RestaurantCriteria criteria) {
        var user = SecurityUtils.getCurrentUserAuth();
        return restaurantGateway.findAll(criteria);
    }
}

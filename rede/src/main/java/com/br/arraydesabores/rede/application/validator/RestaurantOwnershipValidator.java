package com.br.arraydesabores.rede.application.validator;

import com.br.arraydesabores.rede.application.exception.ForbiddenException;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;

public class RestaurantOwnershipValidator {

    public static void IsOwned(Restaurant restaurant) {
        var user = SecurityUtils.getCurrentUserAuth();
        if (!restaurant.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Você não tem permissão para gerenciar este restaurante");
        }
    }
}

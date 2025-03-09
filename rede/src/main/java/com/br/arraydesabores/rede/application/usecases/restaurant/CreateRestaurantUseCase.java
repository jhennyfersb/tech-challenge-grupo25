package com.br.arraydesabores.rede.application.usecases.restaurant;


import com.br.arraydesabores.rede.application.exception.RestaurantCreateForbiddenException;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantCreateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRestaurantUseCase {

    private final IRestaurantGateway restaurantGateway;
    private final ModelMapper modelMapper;

    public Restaurant execute(RestaurantCreateRequest input) {
        var user = SecurityUtils.getCurrentUserAuth();
        if (user == null
                || user.getRoles().stream().noneMatch(role -> role.getRole().equals(UserRoleType.OWNER))) {
            throw new RestaurantCreateForbiddenException();
        }
        var restaurant = modelMapper.map(input, Restaurant.class);
        restaurant.setOwner(modelMapper.map(user, User.class));
        return restaurantGateway.save(restaurant);
    }
}

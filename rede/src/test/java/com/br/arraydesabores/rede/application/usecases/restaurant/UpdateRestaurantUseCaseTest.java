package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;
import com.br.arraydesabores.rede.domain.enums.CuisineType;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantUpdateRequest;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UpdateRestaurantUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    AutoCloseable mock;

    @BeforeEach
    public void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        mock.close();
    }

    @Test
    @DisplayName("Deve atualizar um restaurante")
    void shouldUpdateRestaurant() {
        var restaurantUpdateRequest = new RestaurantUpdateRequest();
        restaurantUpdateRequest.setName("Teste Atualizado");

        var restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Teste");
        restaurant.setOwner(new User(1L));
        restaurant.setCuisineType(CuisineType.BRAZILIAN);

        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(restaurantGateway.findById(1L)).thenReturn(restaurant);
        doNothing().when(modelMapper).map(restaurantUpdateRequest, restaurant);
        when(restaurantGateway.save(restaurant)).thenReturn(restaurant);

        var result = updateRestaurantUseCase.execute(1L, restaurantUpdateRequest);

        assertEquals(result.getName(), restaurant.getName());
    }

}
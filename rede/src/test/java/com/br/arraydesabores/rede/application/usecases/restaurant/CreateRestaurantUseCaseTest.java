package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.application.usecases.user.CreateUserUseCase;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantCreateRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateRestaurantUseCaseTest {
    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

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
    @DisplayName("Deve criar um restaurante autenticado")
    @WithMockUser(roles = "OWNER")
    void shouldCreateAuthenticatedRestaurant() {

        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
        restaurantCreateRequest.setName("Restaurante do Zé");

        var userAuth = new UserAuthDTO();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var restaurant = new Restaurant();
        restaurant.setName("Restaurante do Zé");

        when(modelMapper.map(restaurantCreateRequest, Restaurant.class)).thenReturn(restaurant);
        when(modelMapper.map(userAuth, User.class)).thenReturn(new User());
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(restaurant);

        var restaurantSave = createRestaurantUseCase.execute(restaurantCreateRequest);

        assertNotNull(restaurant);
        assertEquals("Restaurante do Zé", restaurantSave.getName());

    }

}
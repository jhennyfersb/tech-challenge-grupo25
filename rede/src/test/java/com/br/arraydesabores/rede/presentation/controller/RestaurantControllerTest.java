package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.usecases.restaurant.CreateRestaurantUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.DeleteRestaurantUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.FindAllRestaurantsUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.FindByIdRestaurantsUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.UpdateRestaurantUseCase;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantCreateRequest;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantResponse;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantUpdateRequest;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import com.br.arraydesabores.rede.presentation.dto.userRole.UserRoleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private FindAllRestaurantsUseCase findAllRestaurantsUseCase;

    @Mock
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Mock
    private FindByIdRestaurantsUseCase findByIdRestaurantsUseCase;

    @Mock
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        var userRole = new UserRoleDTO();
        userRole.setRole(UserRoleType.OWNER);

        userAuth.setRoles(Set.of(userRole));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("Deve cadastrar um restaurante com sucesso")
    @WithMockUser(roles = "OWNER")
    void shouldCreateRestaurantSuccessfully() {
        var restaurantCreateRequest = new RestaurantCreateRequest();

        var restaurant = new Restaurant();
        when(createRestaurantUseCase.execute(restaurantCreateRequest)).thenReturn(restaurant);
        when(modelMapper.map(restaurant, RestaurantResponse.class)).thenReturn(new RestaurantResponse());

        var response = restaurantController.create(restaurantCreateRequest);

        assertNotNull(response);
        verify(createRestaurantUseCase, times(1)).execute(restaurantCreateRequest);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia de restaurantes")
    void shouldFindAllEmptyRestaurantsSuccessfully() {
        var restaurantCriteria = new RestaurantCriteria();

        when(findAllRestaurantsUseCase.execute(restaurantCriteria)).thenReturn(Page.empty());

        var response = restaurantController.findAll(restaurantCriteria);

        assertNotNull(response);
        verify(findAllRestaurantsUseCase, times(1)).execute(restaurantCriteria);
    }

    @Test
    @DisplayName("Deve retornar uma lista de restaurantes")
    void shouldFindAllRestaurantsSuccessfully() {
        var restaurantCriteria = new RestaurantCriteria();
        var restaurant = new Restaurant();
        var restaurantPage = new PageImpl<>(List.of(restaurant));
        when(findAllRestaurantsUseCase.execute(restaurantCriteria)).thenReturn(restaurantPage);
        when(modelMapper.map(restaurant, RestaurantResponse.class)).thenReturn(new RestaurantResponse());

        var response = restaurantController.findAll(restaurantCriteria);

        assertNotNull(response);
        assertEquals(1, Objects.requireNonNull(response.getBody()).getContent().size());
        verify(findAllRestaurantsUseCase, times(1)).execute(restaurantCriteria);
    }

    @Test
    @DisplayName("Deve retornar um restaurante por id")
    void shouldFindRestaurantByIdSuccessfully() {
        var restaurant = new Restaurant();
        when(findByIdRestaurantsUseCase.execute(1L)).thenReturn(restaurant);
        when(modelMapper.map(restaurant, RestaurantResponse.class)).thenReturn(new RestaurantResponse());

        var response = restaurantController.findById(1L);

        assertNotNull(response);
        verify(findByIdRestaurantsUseCase, times(1)).execute(1L);
    }

    @Test
    @DisplayName("Deve atualizar um restaurante com sucesso")
    @WithMockUser(roles = "OWNER")
    void shouldUpdateRestaurantSuccessfully() {
        var restaurantUpdateRequest = new RestaurantUpdateRequest();
        var restaurant = new Restaurant();
        when(updateRestaurantUseCase.execute(1L, restaurantUpdateRequest)).thenReturn(restaurant);
        when(modelMapper.map(restaurant, RestaurantResponse.class)).thenReturn(new RestaurantResponse());

        var response = restaurantController.update(1L, restaurantUpdateRequest);

        assertNotNull(response);
        verify(updateRestaurantUseCase, times(1)).execute(1L, restaurantUpdateRequest);
    }

    @Test
    @DisplayName("Deve deletar um restaurante com sucesso")
    @WithMockUser(roles = "OWNER")
    void shouldDeleteRestaurantSuccessfully() {
        restaurantController.delete(1L);

        verify(deleteRestaurantUseCase, times(1)).execute(1L);
    }

}
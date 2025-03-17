package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.enums.CuisineType;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindByIdRestaurantsUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;

    @InjectMocks
    private FindByIdRestaurantsUseCase findByIdRestaurantsUseCase;

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
    @DisplayName("Retorna restaurante por Id")
    void shouldReturnRestaurantById() {
        var restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Teste");
        restaurant.setOwner(new User(1L));
        restaurant.setCuisineType(CuisineType.BRAZILIAN);

        when(restaurantGateway.findById(1L)).thenReturn(restaurant);

        var result = findByIdRestaurantsUseCase.execute(1L);

        assertEquals(restaurant.getId(), result.getId());
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getOwner(), result.getOwner());
        assertEquals(restaurant.getCuisineType(), result.getCuisineType());
    }

}
package com.br.arraydesabores.rede.application.usecases.restaurant;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindAllRestaurantsUseCaseTest {

    @Mock
    private IRestaurantGateway restaurantGateway;

    @InjectMocks
    private FindAllRestaurantsUseCase findAllRestaurantsUseCase;

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
    @DisplayName("Retorna lista de restaurantes")
    void shouldReturnAllRestaurants() {
        var restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Teste");
        restaurant.setOwner(new User(1L));
        restaurant.setCuisineType(CuisineType.BRAZILIAN);

        List<Restaurant> content = Collections.singletonList(restaurant);
        Page<Restaurant> restaurants = new PageImpl<>(content);

        when(restaurantGateway.findAll(any(RestaurantCriteria.class))).thenReturn(restaurants);

        var result = findAllRestaurantsUseCase.execute(new RestaurantCriteria());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().getFirst().getId());
        assertEquals("Teste", result.getContent().getFirst().getName());
        assertEquals(CuisineType.BRAZILIAN, result.getContent().getFirst().getCuisineType());
        assertEquals(1L, result.getContent().getFirst().getOwner().getId());
    }


    @Test
    @DisplayName("Retorna lista vazia de restaurantes")
    void shouldReturnEmptyRestaurants() {
        var criteria = new RestaurantCriteria();

        when(restaurantGateway.findAll(any(RestaurantCriteria.class))).thenReturn(Page.empty());

        var result = findAllRestaurantsUseCase.execute(criteria);

        assertEquals(0, result.getContent().size());
    }

}
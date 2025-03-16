package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.exception.RestaurantNotFoundException;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.repository.RestaurantRepository;
import com.br.arraydesabores.rede.infrastructure.specifications.RestaurantSpecifications;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantGatewayImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RestaurantGatewayImpl restaurantGateway;

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
    @Description("Deve retornar todos os restaurantes")
    void shouldReturnAllRestaurants() {
        var restaurant = new Restaurant();
        var criteria = new RestaurantCriteria();
        var restaurantEntity = new RestaurantEntity();
        var page = new PageImpl<>(List.of(restaurantEntity));
        when(restaurantRepository.findAll(RestaurantSpecifications.where(criteria), criteria.getPageable())).thenReturn(page);
        when(modelMapper.map(restaurantEntity, Restaurant.class)).thenReturn(restaurant);

        var result = restaurantGateway.findAll(criteria);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @Description("Deve retornar um restaurante pelo id")
    void shouldReturnRestaurantById() {
        var restaurant = new Restaurant();
        var restaurantEntity = new RestaurantEntity();
        when(restaurantRepository.findById(1L)).thenReturn(java.util.Optional.of(restaurantEntity));
        when(modelMapper.map(restaurantEntity, Restaurant.class)).thenReturn(restaurant);

        var result = restaurantGateway.findById(1L);

        assertNotNull(result);
    }

    @Test
    @Description("Deve retornar uma exception ao buscar um restaurante pelo id")
    void shouldReturnExceptionWhenRestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> restaurantGateway.findById(1L));
    }

    @Test
    @Description("Deve salvar um restaurante")
    void shouldSaveRestaurant() {
        var restaurant = new Restaurant();
        var restaurantEntity = new RestaurantEntity();
        when(modelMapper.map(restaurant, RestaurantEntity.class)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(modelMapper.map(restaurantEntity, Restaurant.class)).thenReturn(restaurant);

        var result = restaurantGateway.save(restaurant);

        assertNotNull(result);
    }

    @Test
    @Description("Deve deletar um restaurante")
    void shouldDeleteRestaurant() {
        var restaurant = new Restaurant();
        restaurant.setId(1L);

        doNothing().when(restaurantRepository).deleteById(1L);

        restaurantGateway.delete(restaurant);

        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    @Description("Deve retornar todos os restaurantes de um dono")
    void shouldReturnAllRestaurantsByOwner() {
        var restaurant = new Restaurant();
        var owner = new UserEntity();
        owner.setId(1L);

        var restaurantEntity = new RestaurantEntity();
        restaurantEntity.setOwner(owner);

        when(restaurantRepository.findByOwnerId(owner.getId())).thenReturn(List.of(restaurantEntity));
        when(modelMapper.map(restaurantEntity, Restaurant.class)).thenReturn(restaurant);

        var result = restaurantGateway.findByOwner(new User(1L));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @Description("Deve verificar se um restaurante existe")
    void shouldCheckIfRestaurantExists() {
        when(restaurantRepository.existsById(1L)).thenReturn(true);

        var result = restaurantGateway.existsById(1L);

        assertTrue(result);
    }

}
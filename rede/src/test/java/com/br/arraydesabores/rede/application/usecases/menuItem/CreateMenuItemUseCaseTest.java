package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.application.usecases.restaurant.CreateRestaurantUseCase;
import com.br.arraydesabores.rede.application.validator.RestaurantOwnershipValidator;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemCreateRequest;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CreateMenuItemUseCaseTest {

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

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
    @DisplayName("Deve criar um item para o menu do restaurante com sucesso")
    @WithMockUser(roles = "OWNER")
    void shouldCreateMenuItemSuccessfully() {
        var restaurantId = 1L;

        var restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwner(new User(1L));

        var menuItemCreateRequest = new MenuItemCreateRequest();
        menuItemCreateRequest.setName("Coxinha");
        menuItemCreateRequest.setPrice(5.0);

        var menuItem = new MenuItem();
        menuItem.setName("Coxinha");
        menuItem.setPrice(BigDecimal.valueOf(5.0));

        restaurant.setMenuItems(List.of(menuItem));

        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(restaurantGateway.findById(restaurantId)).thenReturn(restaurant);
        when(modelMapper.map(menuItemCreateRequest, MenuItem.class)).thenReturn(menuItem);
        when(menuItemGateway.save(restaurant, menuItem)).thenReturn(menuItem);

        var menuItemSaved = createMenuItemUseCase.execute(restaurantId, menuItemCreateRequest);

        assertNotNull(restaurant);
        assertEquals(menuItemSaved.getName(), menuItem.getName());
        assertEquals(menuItemSaved.getPrice(), menuItem.getPrice());

    }


    @Test
    @DisplayName("Deve lançar exceção quando usuário não tiver permissão para criar item no menu")
    @WithMockUser(roles = "OWNER")
    void shouldThrowExceptionWhenUserDoesNotHavePermissionToCreateMenuItem() {
        var restaurantId = 1L;

        var restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwner(new User(2L));

        var menuItemCreateRequest = new MenuItemCreateRequest();
        menuItemCreateRequest.setName("Coxinha");
        menuItemCreateRequest.setPrice(5.0);

        var menuItem = new MenuItem();
        menuItem.setName("Coxinha");
        menuItem.setPrice(BigDecimal.valueOf(5.0));

        restaurant.setMenuItems(List.of(menuItem));

        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(restaurantGateway.findById(restaurantId)).thenReturn(restaurant);

        try {
            createMenuItemUseCase.execute(restaurantId, menuItemCreateRequest);
        } catch (Exception e) {
            assertEquals("Você não tem permissão para gerenciar este restaurante", e.getMessage());
        }
    }
}
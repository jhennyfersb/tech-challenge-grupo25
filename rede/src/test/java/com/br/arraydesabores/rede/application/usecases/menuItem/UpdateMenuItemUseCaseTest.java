package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemUpdateRequest;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class UpdateMenuItemUseCaseTest {

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateMenuItemUseCase updateMenuItemUseCase;

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
    @Description("Deve atualizar um item do menu do restaurante com sucesso")
    public void shouldUpdateMenuItemSuccessfully() {
        // given
        Long restaurantId = 1L;
        Long id = 1L;
        MenuItemUpdateRequest input = new MenuItemUpdateRequest();
        input.setName("Item 1");
        input.setDescription("Item 1 description");
        input.setPrice(10.0);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwner(new User(1L));

        MenuItem item = new MenuItem();
        item.setId(id);
        item.setName("Item 1");
        item.setDescription("Item 1 description");
        item.setPrice(BigDecimal.valueOf(10.0));

        when(restaurantGateway.findById(restaurantId)).thenReturn(restaurant);
        when(menuItemGateway.findById(id)).thenReturn(item);
        when(menuItemGateway.save(restaurant, item)).thenReturn(item);

        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when
        MenuItem result = updateMenuItemUseCase.execute(restaurantId, id, input);

        // then
        assertNotNull(result);
        assertEquals("Item 1", result.getName());
        assertEquals("Item 1 description", result.getDescription());
        assertEquals(BigDecimal.valueOf(10.0), result.getPrice());
    }

    @Test
    @Description("Deve lançar exceção quando não for o dono do restaurante")
    public void shouldThrowExceptionWhenNotRestaurantOwner() {
        // given
        Long restaurantId = 1L;
        Long id = 1L;
        MenuItemUpdateRequest input = new MenuItemUpdateRequest();
        input.setName("Item 1");
        input.setDescription("Item 1 description");
        input.setPrice(10.0);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwner(new User(2L));

        MenuItem item = new MenuItem();
        item.setId(id);
        item.setName("Item 1");
        item.setDescription("Item 1 description");
        item.setPrice(BigDecimal.valueOf(10.0));

        when(restaurantGateway.findById(restaurantId)).thenReturn(restaurant);
        when(menuItemGateway.findById(id)).thenReturn(item);

        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when
        try {
            updateMenuItemUseCase.execute(restaurantId, id, input);
        } catch (Exception e) {
            // then
            assertEquals("Você não tem permissão para gerenciar este restaurante", e.getMessage());
        }
    }



}
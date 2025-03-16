package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
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

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteMenuItemUseCaseTest {

    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

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
    @Description("Deve deletar um item do menu do restaurante com sucesso")
    public void deleteMenuItem() {
        var restaurantId = 1L;

        var restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwner(new User(1L));

        var menuItem = new MenuItem();
        menuItem.setName("Coxinha");

        restaurant.setMenuItems(List.of(menuItem));

        var userAuth = new UserAuthDTO();
        userAuth.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(restaurantGateway.findById(restaurantId)).thenReturn(restaurant);
        when(menuItemGateway.findById(menuItem.getId())).thenReturn(menuItem);
        doNothing().when(menuItemGateway).deleteById(menuItem.getId());

        deleteMenuItemUseCase.execute(restaurantId, menuItem.getId());

        verify(menuItemGateway, times(1)).deleteById(menuItem.getId());
    }
}
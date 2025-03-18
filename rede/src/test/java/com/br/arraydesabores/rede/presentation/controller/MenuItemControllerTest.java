package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.application.usecases.menuItem.CreateMenuItemUseCase;
import com.br.arraydesabores.rede.application.usecases.menuItem.DeleteMenuItemUseCase;
import com.br.arraydesabores.rede.application.usecases.menuItem.FindAllMenuItemUseCase;
import com.br.arraydesabores.rede.application.usecases.menuItem.UpdateMenuItemUseCase;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemCreateRequest;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemResponse;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemUpdateRequest;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CreateMenuItemUseCase createItemUseCase;

    @Mock
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @Mock
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Mock
    private FindAllMenuItemUseCase findAllMenuItemsUseCase;

    @InjectMocks
    private MenuItemController menuItemController;

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
   @DisplayName("Deve criar um item de menu")
    void shouldCreateMenuItem() {
        var request = new MenuItemCreateRequest();
        var response = new MenuItemResponse();
        var menuItem = new MenuItem();

        when(createItemUseCase.execute(1L, request)).thenReturn(menuItem);
        when(modelMapper.map(menuItem, MenuItemResponse.class)).thenReturn(response);

        var result = menuItemController.create(1L, request);

        assertNotNull(result);
        assertEquals(response, result.getBody());
        verify(createItemUseCase, times(1)).execute(1L, request);
        verify(modelMapper, times(1)).map(menuItem, MenuItemResponse.class);
    }

    @Test
    @DisplayName("Deve atualizar um item de menu")
    void shouldUpdateMenuItem() {
        var request = new MenuItemUpdateRequest();
        var response = new MenuItemResponse();
        var menuItem = new MenuItem();

        when(updateMenuItemUseCase.execute(1L, 1L, request)).thenReturn(menuItem);
        when(modelMapper.map(menuItem, MenuItemResponse.class)).thenReturn(response);

        var result = menuItemController.update(1L, 1L, request);

        assertNotNull(result);
        assertEquals(response, result.getBody());
        verify(updateMenuItemUseCase, times(1)).execute(1L, 1L, request);
        verify(modelMapper, times(1)).map(menuItem, MenuItemResponse.class);
    }

    @Test
    @DisplayName("Deve deletar um item de menu")
    void shouldDeleteMenuItem() {
        menuItemController.delete(1L, 1L);

        verify(deleteMenuItemUseCase, times(1)).execute(1L, 1L);
    }

    @Test
    @DisplayName("Deve retornar uma lista de itens de menu")
    void shouldFindAllMenuItems() {
        var criteria = new MenuItemCriteria();
        var menuItem = new MenuItem();
        var menuItemPage = new PageImpl<>(List.of(menuItem));
        when(findAllMenuItemsUseCase.execute(1L, criteria)).thenReturn(menuItemPage);
        when(modelMapper.map(menuItem, MenuItemResponse.class)).thenReturn(new MenuItemResponse());

        var result = menuItemController.findAll(1L, criteria);

        assertNotNull(result);
        assertEquals(1, Objects.requireNonNull(result.getBody()).getContent().size());
        verify(findAllMenuItemsUseCase, times(1)).execute(1L, criteria);
    }


}
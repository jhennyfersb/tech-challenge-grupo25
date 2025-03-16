package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.infrastructure.entity.MenuItemEntity;
import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import com.br.arraydesabores.rede.infrastructure.repository.MenuItemRepository;
import com.br.arraydesabores.rede.infrastructure.specifications.MenuItemSpecifications;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuItemGatewayImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MenuItemGatewayImpl menuItemGateway;

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
    @Description("Deve retornar os itens do menu de um restaurante")
    void shouldReturnMenuItemsFromRestaurant() {
        var criteria = new MenuItemCriteria();
        var menuItemEntity = new MenuItemEntity();
        var menuItem = new MenuItem();
        var page = new PageImpl<>(List.of(menuItemEntity));
        when(menuItemRepository.findAll(MenuItemSpecifications.where(criteria), criteria.getPageable())).thenReturn(page);
        when(modelMapper.map(menuItemEntity, MenuItem.class)).thenReturn(menuItem);

        var result = menuItemGateway.findAllByRestaurantId(criteria);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).contains(menuItem);
    }

    @Test
    @Description("Deve retornar um item do menu pelo id")
    void shouldReturnMenuItemById() {
        var menuItemEntity = new MenuItemEntity();
        var menuItem = new MenuItem();
        menuItem.setId(1L);
        when(menuItemRepository.findById(1L)).thenReturn(java.util.Optional.of(menuItemEntity));
        when(modelMapper.map(menuItemEntity, MenuItem.class)).thenReturn(menuItem);

        var result = menuItemGateway.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(menuItem);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @Description("Deve salvar um item do menu")
    void shouldSaveMenuItem() {
        var menuItemEntity = new MenuItemEntity();
        var menuItem = new MenuItem();
        var restaurant = new Restaurant();
        when(modelMapper.map(menuItem, MenuItemEntity.class)).thenReturn(menuItemEntity);
        when(modelMapper.map(restaurant, RestaurantEntity.class)).thenReturn(new RestaurantEntity());
        when(menuItemRepository.save(menuItemEntity)).thenReturn(menuItemEntity);
        when(modelMapper.map(menuItemEntity, MenuItem.class)).thenReturn(menuItem);

        var result = menuItemGateway.save(restaurant, menuItem);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(menuItem);
    }

    @Test
    @Description("Deve deletar um item do menu pelo id")
    void shouldDeleteMenuItemById() {
        doNothing().when(menuItemRepository).deleteById(1L);

        menuItemGateway.deleteById(1L);

        verify(menuItemRepository, times(1)).deleteById(1L);
    }

}
package com.br.arraydesabores.rede.application.usecases.menuItem;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindAllMenuItemUseCaseTest {
    @Mock
    private IMenuItemGateway menuItemGateway;

    @Mock
    private IRestaurantGateway restaurantGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FindAllMenuItemUseCase findAllMenuItemUseCase;

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
    @Description("Deve retornar uma lista de itens do menu do restaurante com sucesso")
    void shouldReturnAListOfMenuItemsFromRestaurantSuccessfully() {
        Long restaurantId = 1L;
        MenuItemCriteria criteria = new MenuItemCriteria();
        criteria.setRestaurantId(restaurantId);

        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);

        List<MenuItem> content = Collections.singletonList(menuItem);
        Page<MenuItem> menuItemPage = new PageImpl<>(content);

        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(menuItemGateway.findAllByRestaurantId(criteria)).thenReturn(menuItemPage);

        var result = findAllMenuItemUseCase.execute(restaurantId, criteria);

        assertFalse(result.isEmpty());
        assertNotNull(findAllMenuItemUseCase.execute(restaurantId, criteria));
        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().getFirst().getId());
    }

    @Test
    @Description("Deve retornar um Page vazio quando o restaurante não existe")
    void shouldReturnAnEmptyPageWhenRestaurantDoesNotExist() {
        Long restaurantId = 1L;
        MenuItemCriteria criteria = new MenuItemCriteria();
        criteria.setRestaurantId(restaurantId);

        when(restaurantGateway.existsById(restaurantId)).thenReturn(false);

        var result = findAllMenuItemUseCase.execute(restaurantId, criteria);

        assertTrue(result.isEmpty());
        assertEquals(0, result.getContent().size());
    }


}
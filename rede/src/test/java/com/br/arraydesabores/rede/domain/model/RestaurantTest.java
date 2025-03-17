package com.br.arraydesabores.rede.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    @DisplayName("Deve criar lista vazia de menu items")
    void shouldCreateEmptyMenuItemsList() {
        Restaurant restaurant = new Restaurant();
        assertTrue(restaurant.getMenuItems().isEmpty());
    }


}
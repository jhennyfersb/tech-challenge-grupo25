package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;

import java.util.List;

public interface IMenuItemGateway {

    List<MenuItem> findAllByRestaurant(Restaurant restaurant);

    MenuItem findById(Long id);

    MenuItem save(MenuItem menuItem);

    void deleteById(Long id);

}

package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import org.springframework.data.domain.Page;


public interface IMenuItemGateway {

    Page<MenuItem> findAllByRestaurantId(MenuItemCriteria criteria);

    MenuItem findById(Long id);

    MenuItem save(Restaurant restaurant, MenuItem menuItem);

    void deleteById(Long id);

}

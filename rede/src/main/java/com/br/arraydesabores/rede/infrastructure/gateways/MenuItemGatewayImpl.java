package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.application.exception.MenuItemNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.infrastructure.entity.MenuItemEntity;
import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import com.br.arraydesabores.rede.infrastructure.repository.MenuItemRepository;
import com.br.arraydesabores.rede.infrastructure.specifications.MenuItemSpecifications;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemGatewayImpl implements IMenuItemGateway {

    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<MenuItem> findAllByRestaurantId(MenuItemCriteria criteria) {
        return menuItemRepository.findAll(MenuItemSpecifications.where(criteria), criteria.getPageable())
                .map(entity -> modelMapper.map(entity, MenuItem.class));
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .map(entity -> modelMapper.map(entity, MenuItem.class))
                .orElseThrow(MenuItemNotFoundException::new);
    }

    @Override
    public MenuItem save(Restaurant restaurant, MenuItem menuItem) {
        var entity = modelMapper.map(menuItem, MenuItemEntity.class);
        entity.setRestaurant(modelMapper.map(restaurant, RestaurantEntity.class));
        return modelMapper.map(menuItemRepository.save(entity), MenuItem.class);
    }

    @Override
    public void deleteById(Long id) {
        menuItemRepository.deleteById(id);
    }
}

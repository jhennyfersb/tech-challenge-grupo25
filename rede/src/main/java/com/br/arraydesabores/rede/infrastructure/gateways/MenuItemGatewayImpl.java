package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.exception.MenuItemNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IMenuItemGateway;
import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.infrastructure.entity.MenuItemEntity;
import com.br.arraydesabores.rede.infrastructure.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemGatewayImpl implements IMenuItemGateway {

    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<MenuItem> findAllByRestaurant(Restaurant restaurant) {
        return menuItemRepository.findByRestaurantId(restaurant.getId())
                .stream()
                .map(entity -> modelMapper.map(entity, MenuItem.class))
                .toList();
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .map(entity -> modelMapper.map(entity, MenuItem.class))
                .orElseThrow(MenuItemNotFoundException::new);
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        var entity = modelMapper.map(menuItem, MenuItemEntity.class);
        return modelMapper.map(menuItemRepository.save(entity), MenuItem.class);
    }

    @Override
    public void deleteById(Long id) {
        menuItemRepository.deleteById(id);
    }
}

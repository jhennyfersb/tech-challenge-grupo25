package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.model.MenuItem;
import com.br.arraydesabores.rede.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getAll() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getById(Long id) {
        return menuItemRepository.findById(id);
    }

    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }
}

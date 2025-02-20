package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.model.MenuItem;
import com.br.arraydesabores.rede.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciamento dos itens do cardápio.
 */
@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItem> getAll() {
        return menuItemService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable Long id) {
        return menuItemService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItem menuItem) {
        return menuItemService.save(menuItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> update(@PathVariable Long id, @RequestBody MenuItem updatedMenuItem) {
        return menuItemService.getById(id)
                .map(existing -> {
                    existing.setName(updatedMenuItem.getName());
                    existing.setDescription(updatedMenuItem.getDescription());
                    existing.setPrice(updatedMenuItem.getPrice());
                    existing.setApenasNoLocal(updatedMenuItem.isApenasNoLocal());
                    existing.setFotoPath(updatedMenuItem.getFotoPath());
                    return ResponseEntity.ok(menuItemService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.ok().build();
    }
}

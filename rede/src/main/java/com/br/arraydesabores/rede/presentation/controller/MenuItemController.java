package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.application.security.HasRole;
import com.br.arraydesabores.rede.application.usecases.menuItem.CreateMenuItemUseCase;
import com.br.arraydesabores.rede.application.usecases.menuItem.DeleteMenuItemUseCase;
import com.br.arraydesabores.rede.application.usecases.menuItem.FindAllMenuItemUseCase;
import com.br.arraydesabores.rede.application.usecases.menuItem.UpdateMenuItemUseCase;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemCreateRequest;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemResponse;
import com.br.arraydesabores.rede.presentation.dto.menuItem.MenuItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final ModelMapper modelMapper;
    private final CreateMenuItemUseCase createItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final FindAllMenuItemUseCase findAllMenuItemsUseCase;

    @PostMapping
    @PreAuthorize(HasRole.OWNER)
    public ResponseEntity<MenuItemResponse> create(@PathVariable("restaurantId") Long restaurantId,
                                                   @RequestBody MenuItemCreateRequest request) {
        var response = createItemUseCase.execute(restaurantId, request);
        return ResponseEntity.ok(modelMapper.map(response, MenuItemResponse.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize(HasRole.OWNER)
    public ResponseEntity<MenuItemResponse> update(@PathVariable("restaurantId") Long restaurantId,
                                                   @PathVariable("id") Long id,
                                                   @RequestBody MenuItemUpdateRequest request) {
        var response = updateMenuItemUseCase.execute(restaurantId, id, request);
        return ResponseEntity.ok(modelMapper.map(response, MenuItemResponse.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(HasRole.OWNER)
    public ResponseEntity<Void> delete(@PathVariable("restaurantId") Long restaurantId,
                                       @PathVariable("id") Long id) {
        deleteMenuItemUseCase.execute(restaurantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<MenuItemResponse>> findAll(@PathVariable("restaurantId") Long restaurantId,
                                                          MenuItemCriteria criteria) {
        return ResponseEntity.ok(findAllMenuItemsUseCase.execute(restaurantId, criteria).map(item -> modelMapper.map(item, MenuItemResponse.class)));
    }


}

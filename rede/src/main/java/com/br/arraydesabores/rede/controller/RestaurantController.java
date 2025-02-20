package com.br.arraydesabores.rede.controller;


import com.br.arraydesabores.rede.model.Restaurant;
import com.br.arraydesabores.rede.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable Long id) {
        return restaurantService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        return restaurantService.getById(id)
                .map(existing -> {
                    existing.setName(updatedRestaurant.getName());
                    existing.setAddress(updatedRestaurant.getAddress());
                    existing.setTipoCozinha(updatedRestaurant.getTipoCozinha());
                    existing.setHorarioFuncionamento(updatedRestaurant.getHorarioFuncionamento());
                    existing.setOwner(updatedRestaurant.getOwner());
                    return ResponseEntity.ok(restaurantService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.ok().build();
    }
}

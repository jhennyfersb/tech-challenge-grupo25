package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.security.HasRole;
import com.br.arraydesabores.rede.application.usecases.restaurant.CreateRestaurantUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.DeleteRestaurantUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.FindAllRestaurantsUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.FindByIdRestaurantsUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.UpdateRestaurantUseCase;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantCreateRequest;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantResponse;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantUpdateRequest;
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
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final ModelMapper modelMapper;
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FindAllRestaurantsUseCase findAllRestaurantsUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final FindByIdRestaurantsUseCase findByIdRestaurantsUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    @PostMapping
    @PreAuthorize(HasRole.OWNER)
    public ResponseEntity<RestaurantResponse> create(@RequestBody RestaurantCreateRequest restaurantRequest) {
        var response = createRestaurantUseCase.execute(restaurantRequest);
        return ResponseEntity.ok(modelMapper.map(response, RestaurantResponse.class));
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> findAll(RestaurantCriteria criteria) {
        var responses = findAllRestaurantsUseCase.execute(criteria);
        var result = responses.map(res -> modelMapper.map(res, RestaurantResponse.class));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize(HasRole.OWNER)
    public ResponseEntity<RestaurantResponse> update(@PathVariable("id") Long id,
                                                     @RequestBody RestaurantUpdateRequest restaurantRequest) {
        var response = updateRestaurantUseCase.execute(id, restaurantRequest);
        return ResponseEntity.ok(modelMapper.map(response, RestaurantResponse.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable("id") Long id) {
        var response = findByIdRestaurantsUseCase.execute(id);
        return ResponseEntity.ok(modelMapper.map(response, RestaurantResponse.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(HasRole.OWNER)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}

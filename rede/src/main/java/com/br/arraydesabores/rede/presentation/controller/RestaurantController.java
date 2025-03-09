package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.usecases.restaurant.CreateRestaurantUseCase;
import com.br.arraydesabores.rede.application.usecases.restaurant.FindAllRestaurantsUseCase;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantCreateRequest;
import com.br.arraydesabores.rede.presentation.dto.restaurant.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@RequestBody RestaurantCreateRequest restaurantRequest) {
        return ResponseEntity.ok(modelMapper.map(createRestaurantUseCase.execute(restaurantRequest), RestaurantResponse.class));
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> findAll(RestaurantCriteria criteria) {
        var responses = findAllRestaurantsUseCase.execute(criteria);
        var result = responses.map(res -> modelMapper.map(res, RestaurantResponse.class));
        return ResponseEntity.ok(result);
    }

}

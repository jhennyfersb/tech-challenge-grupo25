package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.application.exception.RestaurantNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IRestaurantGateway;
import com.br.arraydesabores.rede.infrastructure.specifications.RestaurantSpecifications;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import com.br.arraydesabores.rede.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantGatewayImpl implements IRestaurantGateway {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<Restaurant> findAll(RestaurantCriteria criteria) {
        var pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        return restaurantRepository.findAll(RestaurantSpecifications.where(criteria), pageable)
                .map(item -> modelMapper.map(item, Restaurant.class));
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Restaurant.class))
                .orElseThrow(RestaurantNotFoundException::new);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        var entity = restaurantRepository.save(modelMapper.map(restaurant, RestaurantEntity.class));
        return modelMapper.map(entity, Restaurant.class);
    }

    @Override
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> findByOwner(User owner) {
        return restaurantRepository.findByOwnerId(owner.getId())
                .stream()
                .map(entity -> modelMapper.map(entity, Restaurant.class))
                .toList();
    }
}

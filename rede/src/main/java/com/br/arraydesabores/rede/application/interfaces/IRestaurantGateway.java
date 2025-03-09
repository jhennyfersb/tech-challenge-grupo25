package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.domain.model.Restaurant;
import com.br.arraydesabores.rede.domain.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantGateway {

    Page<Restaurant> findAll(RestaurantCriteria criteria);

    Restaurant findById(Long id);

    Restaurant save(Restaurant restaurant);

    void delete(Restaurant restaurant);

    List<Restaurant> findByOwner(User owner);

    boolean existsById(Long id);

}

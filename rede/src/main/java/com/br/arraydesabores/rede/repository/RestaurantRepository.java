package com.br.arraydesabores.rede.repository;

import com.br.arraydesabores.rede.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}

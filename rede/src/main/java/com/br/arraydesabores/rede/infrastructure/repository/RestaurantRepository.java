package com.br.arraydesabores.rede.infrastructure.repository;

import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long>, JpaSpecificationExecutor<RestaurantEntity> {

    List<RestaurantEntity> findByOwnerId(Long userId);
}

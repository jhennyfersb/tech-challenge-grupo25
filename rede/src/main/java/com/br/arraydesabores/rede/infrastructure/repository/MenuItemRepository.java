package com.br.arraydesabores.rede.infrastructure.repository;

import com.br.arraydesabores.rede.domain.model.MenuItem;
import com.br.arraydesabores.rede.infrastructure.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {

    List<MenuItemEntity> findByRestaurantId(Long id);
}

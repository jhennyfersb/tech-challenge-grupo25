package com.br.arraydesabores.rede.infrastructure.repository;

import com.br.arraydesabores.rede.infrastructure.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long>, JpaSpecificationExecutor<MenuItemEntity> {

}

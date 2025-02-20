package com.br.arraydesabores.rede.repository;

import com.br.arraydesabores.rede.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}

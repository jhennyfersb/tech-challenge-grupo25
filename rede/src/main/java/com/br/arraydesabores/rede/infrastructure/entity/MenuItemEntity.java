package com.br.arraydesabores.rede.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemEntity extends BaseMutableEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean availableOnlyInRestaurant;

    @Column()
    private String photoPath;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

}

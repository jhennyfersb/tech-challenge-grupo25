package com.br.arraydesabores.rede.infrastructure.specifications;

import com.br.arraydesabores.rede.application.criteria.MenuItemCriteria;
import com.br.arraydesabores.rede.infrastructure.entity.MenuItemEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class MenuItemSpecifications {

    private MenuItemSpecifications() {
    }

    public static Specification<MenuItemEntity> where(MenuItemCriteria criteria) {
        Specification<MenuItemEntity> spec = Specification.where(null);
        if (criteria != null) {
            if (criteria.getMinPrice() != null) {
                spec = spec.and(MenuItemSpecifications.priceGreaterThan(criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                spec = spec.and(MenuItemSpecifications.priceLessThan(criteria.getMaxPrice()));
            }

            if (criteria.getMinPrice() != null && criteria.getMaxPrice() != null) {
                spec = spec.and(MenuItemSpecifications.priceBetween(criteria.getMinPrice(), criteria.getMaxPrice()));
            }
            if (criteria.getName() != null) {
                spec = spec.and(MenuItemSpecifications.nameLike(criteria.getName()));
            }
        }
        return spec;
    }

    public static Specification<MenuItemEntity> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<MenuItemEntity> priceGreaterThan(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), minPrice);
    }

    public static Specification<MenuItemEntity> priceLessThan(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), maxPrice);
    }

    public static Specification<MenuItemEntity> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }
}


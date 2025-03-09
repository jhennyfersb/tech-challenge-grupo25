package com.br.arraydesabores.rede.application.specifications;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.domain.enums.CuisineType;
import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecifications {

    private RestaurantSpecifications() {
    }

    public static Specification<RestaurantEntity> where(RestaurantCriteria criteria) {
        Specification<RestaurantEntity> spec = Specification.where(null);
        if (criteria != null) {
            if (criteria.getOwnerId() != null) {
                spec = spec.and(RestaurantSpecifications.withOwner(criteria.getOwnerId()));
            }
            if (criteria.getCuisineType() != null) {
                spec = spec.and(RestaurantSpecifications.withCuisineType(criteria.getCuisineType()));
            }
        }
        return spec;
    }

    public static Specification<RestaurantEntity> withName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<RestaurantEntity> withCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("address").get("city")),
                    "%" + city.toLowerCase() + "%"
            );
        };
    }

    public static Specification<RestaurantEntity> withOwner(Long ownerId) {
        return (root, query, criteriaBuilder) -> {
            if (ownerId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
        };
    }

    public static Specification<RestaurantEntity> withCuisineType(CuisineType type) {
        return (root, query, criteriaBuilder) -> {
            if (type == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("cuisineType"), type);
        };
    }
}


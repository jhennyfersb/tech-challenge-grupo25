package com.br.arraydesabores.rede.infrastructure.specifications;

import com.br.arraydesabores.rede.application.criteria.RestaurantCriteria;
import com.br.arraydesabores.rede.domain.enums.CuisineType;
import com.br.arraydesabores.rede.infrastructure.entity.RestaurantEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantSpecificationsTest {

    @Test
    @DisplayName("Testa quando tiver Owner Id")
    void testWithOwnerId() {
        var criteria = new RestaurantCriteria();
        criteria.setOwnerId(1L);

        Specification<RestaurantEntity> spec = RestaurantSpecifications.where(criteria);

        Root<RestaurantEntity> root = mock(Root.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("owner")).thenReturn(mock(Path.class));
        when(root.get("owner").get("id")).thenReturn(mock(Path.class));
        when(cb.equal(any(), eq(1L))).thenReturn(predicate);

        Predicate result = spec.toPredicate(root, null, cb);

        assertNotNull(result);
        verify(cb, times(1)).equal(any(), eq(1L));
    }

    @Test
    @DisplayName("Testa quando tiver Cuisine Type")
    void testWithCuisineType() {
        var criteria = new RestaurantCriteria();
        criteria.setCuisineType(CuisineType.BRAZILIAN);

        Specification<RestaurantEntity> spec = RestaurantSpecifications.where(criteria);

        Root<RestaurantEntity> root = mock(Root.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("cuisineType")).thenReturn(mock(Path.class));
        when(cb.equal(any(), eq(CuisineType.BRAZILIAN))).thenReturn(predicate);

        Predicate result = spec.toPredicate(root, null, cb);

        assertNotNull(result);
        verify(cb, times(1)).equal(any(), eq(CuisineType.BRAZILIAN));
    }

}
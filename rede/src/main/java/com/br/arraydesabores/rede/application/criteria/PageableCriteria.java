package com.br.arraydesabores.rede.application.criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageableCriteria {
    private int page = 0;
    private int size = 10;
    private String sort = "createdAt";
    private String direction = "DESC";

    public Pageable getPageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
    }
}

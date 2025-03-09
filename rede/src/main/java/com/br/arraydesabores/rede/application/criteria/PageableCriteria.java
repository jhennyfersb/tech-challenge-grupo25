package com.br.arraydesabores.rede.application.criteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableCriteria {
    private int page = 0;
    private int size = 10;
    private String sort = "createdAt";
    private String direction = "DESC";

}

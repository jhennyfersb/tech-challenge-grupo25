package com.br.arraydesabores.rede.application.exception;

public class RestaurantCreateForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RestaurantCreateForbiddenException() {
        super("Você não tem permissão para criar um restaurante");
    }
}

package com.br.arraydesabores.rede.application.exception;

public class RestaurantNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException() {
        super("Restaurante não encontrado");
    }
}

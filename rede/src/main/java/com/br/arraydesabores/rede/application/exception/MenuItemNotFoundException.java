package com.br.arraydesabores.rede.application.exception;

public class MenuItemNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MenuItemNotFoundException() {
        super("Item do Menu não encontrado");
    }
}

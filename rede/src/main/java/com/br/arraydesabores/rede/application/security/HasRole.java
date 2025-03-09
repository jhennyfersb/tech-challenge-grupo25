package com.br.arraydesabores.rede.application.security;

public final class HasRole {

    public static final String OWNER = "hasAuthority('OWNER')";
    public static final String ADMIN = "hasAuthority('ADMIN')";
    public static final String USER = "hasAuthority('USER')";
    public static final String CONSUMER = "hasAuthority('CONSUMER')";

    public static final String OWNER_OR_ADMIN = "hasAnyAuthority('OWNER', 'ADMIN')";
    public static final String ADMIN_AND_USER = "hasAnyAuthority('USER', 'ADMIN')";

    private HasRole() {
    }
}
